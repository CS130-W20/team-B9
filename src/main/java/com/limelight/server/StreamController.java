package com.limelight.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/stream", method = RequestMethod.GET)
public class StreamController {
  private static final long CHUNK_SIZE = 1000000L;

  @Autowired
  private AmazonS3ClientService amazonS3ClientService;

  private StreamQueue queue;

  private String currentStreamer;

  private Livestream currentStream;

  private UrlResource urlResource;

  public StreamController() {
    queue = StreamQueue.getInstance();
  }

  /**
   * Serves part of the stream currently at the front of the {@link StreamQueue}
   * contained in the header.
   *
   * @param headers GET request headers that may optionally contain a "Range"
   *                field
   * @return ResponseEntity<ResourceRegion> representing the requested portion of
   *         the video stream
   * @throws Exception if malformed URL is accessed
   */
  @CrossOrigin
  @GetMapping("/get")
  public ResponseEntity<String> getCurrentStream(@RequestHeader HttpHeaders headers) throws Exception {
    String queueStreamer = queue.getCurrentStreamer();
    ResourceRegion region;

    ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT) // return HTTP code 206 to
                                                                                           // indicate partial video
        .cacheControl(CacheControl.noStore().mustRevalidate());

    // if (queueStreamer == null) {
    // currentStreamer = null;
    // urlResource = amazonS3ClientService.getResourceFromS3Bucket("demo.mp4");
    // region = resourceRegion(urlResource, headers);
    // } else if (!queueStreamer.equals(currentStreamer)) {
    // currentStreamer = queueStreamer;
    // urlResource =
    // amazonS3ClientService.getResourceFromS3Bucket(getStreamFromUser(currentStreamer));
    // currentStream = new Livestream(currentStreamer);
    // return null;
    // } else {
    // region = resourceRegion(urlResource, headers);
    // }
    // builder.contentType(MediaTypeFactory.getMediaType(urlResource).orElse(MediaType.APPLICATION_OCTET_STREAM));

    String url = "https://limelight-stream-bucket.s3.us-west-1.amazonaws.com/ucla.mp4";

    if (queueStreamer == null) {
      currentStreamer = null;
      if (currentStream != null) {
        currentStream.clearComments();
        currentStream = null;
      }
    } else if (!queueStreamer.equals(currentStreamer)) {
      currentStreamer = queueStreamer;
      currentStream = new Livestream(currentStreamer);
      url = "https://limelight-stream-bucket.s3.us-west-1.amazonaws.com/" + currentStreamer + ".mp4";
    } else {
      url = "https://limelight-stream-bucket.s3.us-west-1.amazonaws.com/" + currentStreamer + ".mp4";
    }

    return builder.body(url);
  }

  /**
   * Uploads a user's stream video to the S3 database and adds them to the
   * {@link StreamQueue}.
   *
   * @param file     video stream to be uploaded
   * @param userName of streamer
   * @param key      session key
   * @return redirect user to post-upload page
   */
  @PostMapping("/upload")
  public ResponseEntity<String> uploadStream(@RequestPart(value = "file") MultipartFile file,
      @RequestParam String userName, @RequestParam Integer key) {
    // begin uploading user's video stream file and add them to queue
    amazonS3ClientService.uploadFileToS3Bucket(file, true);
    joinStreamQueue(userName, key);

    ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.OK);

    return builder.body("OK");
  }

  /**
   * add user to stream queue
   *
   * @param userName userName to add to stream queue
   * @param key      user's key to ensure it is authorized user
   * @return true if user was added to stream queue, false otherwise
   */
  @PostMapping(path = "/joinStreamQueue")
  public @ResponseBody boolean joinStreamQueue(@RequestParam String userName, @RequestParam Integer key) {
    if (userName.hashCode() != key)
      return false;
    return queue.addStreamer(userName);
  }

  /**
   * remove user from stream queue
   *
   * @param userName userName to remove from stream queue
   * @param key      user's key to ensure it is authorized user
   * @return true if user was removed from stream queue, false otherwise
   */
  @PostMapping(path = "/leaveStreamQueue")
  public @ResponseBody boolean leaveStreamQueue(@RequestParam String userName, @RequestParam Integer key) {
    if (userName.hashCode() != key)
      return false;
    else {
      if (currentStream != null && currentStreamer == queue.getCurrentStreamer()) {
        currentStream.getTimer().timer.cancel();
      }
      return queue.removeStreamer(userName);
    }
  }

  /**
   * Upvotes the current stream.
   */
  @PostMapping("/upvote")
  public @ResponseBody boolean upvoteStream() {
    currentStream.upvote();
    return true;
  }

  /**
   * Downvotes the current stream.
   */
  @PostMapping("/downvote")
  public @ResponseBody boolean downvoteStream() {
    currentStream.downvote();
    return true;
  }

  @CrossOrigin
  @GetMapping("/getCurrentStreamer")
  public @ResponseBody String getCurrentStreamer() {
    if (currentStreamer == null) {
      String dummyStreamer = "Dummy Streamer";
      return dummyStreamer;
    }
    return currentStreamer;
  }

  /**
   * Gets the vote count for the current stream.
   *
   * @return vote count
   */
  @GetMapping("/getVoteCount")
  public @ResponseBody int getVoteCount() {
    return currentStream.getVoteCount();
  }

  /**
   * Gets the time remaining for the current stream.
   *
   * @return time remaining
   */
  @CrossOrigin
  @GetMapping("/getRemainingTime")
  public @ResponseBody long getRemainingTime() {
    if (currentStreamer == null) {
      long dummyStreamerTime = -1;
      return dummyStreamerTime;
    }
    return currentStream.getTimer().getSecondsLeftOfLivestream();
  }

  /**
   * Adds a comment to the current stream.
   *
   * @param userName of commenter
   * @param comment  content of comment
   */
  @PostMapping("/addComment")
  public @ResponseBody boolean addComment(@RequestParam String userName, @RequestParam String comment) {
    currentStream.addComment(userName, comment);
    return true;
  }

  /**
   * Gets list of comments on current stream.
   *
   * @return list of comments
   */
  @CrossOrigin
  @GetMapping("/getComments")
  public @ResponseBody List<LivestreamComment> getComments() {
    return currentStream == null ? new ArrayList<>() : currentStream.getComments();
  }

  /**
   * Returns a portion of a given video based on the "Range" field in the GET
   * request header.
   *
   * @param stream  input stream
   * @param headers GET request headers that may optionally contain a "Range"
   *                field
   * @return ResourceRegion representing the requested portion of the video stream
   * @throws Exception if video length is undefined
   */
  private ResourceRegion resourceRegion(UrlResource stream, HttpHeaders headers) throws Exception {
    long contentLength = stream.contentLength();

    // if header does not contain "Range" field, serve the entire video
    if (headers == null || headers.getRange().isEmpty()) {
      long rangeLength = Math.min(CHUNK_SIZE, contentLength);
      return new ResourceRegion(stream, 0, rangeLength);
    } else {
      HttpRange range = headers.getRange().get(0);
      long start = range.getRangeStart(contentLength);
      long end = range.getRangeEnd(contentLength);
      long rangeLength = Math.min(CHUNK_SIZE, end - start + 1);
      return new ResourceRegion(stream, start, rangeLength);
    }
  }

  /**
   * Returns a user's given stream name according to internal Limelight stream
   * naming conventions.
   *
   * @param userName owner of stream
   * @return file name of user's stream
   */
  private String getStreamFromUser(String userName) {
    return String.format("%s.mp4", userName);
  }
}
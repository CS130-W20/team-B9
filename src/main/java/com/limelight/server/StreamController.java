package com.limelight.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class StreamController {
    private static final long CHUNK_SIZE = 1000000L;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    private StreamQueue queue;

    public StreamController() {
        queue = StreamQueue.getInstance();
    }

    private User currentStreamer;

    private Livestream currentStream;

    /**
     * Serves part of the stream currently at the front of the {@link StreamQueue} contained in the header.
     *
     * @param headers GET request headers that may optionally contain a "Range" field
     * @return ResponseEntity<ResourceRegion> representing the requested portion of the video stream
     * @throws Exception if malformed URL is accessed
     */
    @GetMapping("/stream/get")
    public ResponseEntity<ResourceRegion> getCurrentStream(@RequestHeader HttpHeaders headers) throws Exception {
        User queueStreamer = queue.getCurrentStreamer();

        if (queueStreamer != currentStreamer) {
            currentStreamer = queueStreamer;
            currentStream = new Livestream(currentStreamer);
        }

        // TODO: UrlResource stream = amazonS3ClientService.getResourceFromS3Bucket(getStreamFromUser(queue.getCurrentStreamer()));
        UrlResource stream = amazonS3ClientService.getResourceFromS3Bucket("placeholder");
        ResourceRegion region = resourceRegion(stream, headers);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)    // return HTTP code 206 to indicate partial video
                .contentType(MediaTypeFactory.getMediaType(stream).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    @PostMapping("/stream/upload")
    public RedirectView uploadStream(@RequestPart(value = "file") MultipartFile file, @RequestParam String key) {
        // begin uploading user's video stream file
        amazonS3ClientService.uploadFileToS3Bucket(file, true);

        // TODO
//        queue.addStreamer();
        return new RedirectView("/");
    }

    @PostMapping("/stream/upvote")
    public void upvoteStream() {
        currentStream.upvote();
    }

    @PostMapping("/stream/downvote")
    public void downvoteStream() {
        currentStream.downvote();
    }

    @GetMapping("/stream/getVoteCount")
    public int getVoteCount() {
        return currentStream.getVoteCount();
    }

    @GetMapping("/stream/getRemainingTime")
    public long getRemainingTime() {
        return currentStream.getTimer().getSecondsLeftOfLivestream();
    }

    @PostMapping("/stream/addComment")
    public void addComment(@RequestParam String userName, @RequestParam String comment) {
        currentStream.addComment(userName, comment);
    }

    @GetMapping("/stream/getComments")
    public List<LivestreamComment> getComments() {
        return currentStream.getComments();
    }

    /**
     * Serves the full stream currently at the front of the {@link StreamQueue}.
     * Primarily used for debugging purposes.
     *
     * @return ResponseEntity<UrlResource> representing the full video stream
     * @throws Exception if malformed URL is accessed
     */
    @GetMapping("/stream/get/full")
    private ResponseEntity<UrlResource> getFullVideo() throws Exception {
        UrlResource stream = new UrlResource("file:C:\\Users\\admin\\Desktop\\SampleVideo_720x480_30mb.mp4");
        return ResponseEntity.status(HttpStatus.OK)     // return HTTP code 200 to indicate full video
                .contentType(MediaTypeFactory.getMediaType(stream).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(stream);
    }

    /**
     * Returns a portion of a given video based on the "Range" field in the GET request header.
     *
     * @param stream  input stream
     * @param headers GET request headers that may optionally contain a "Range" field
     * @return ResourceRegion representing the requested portion of the video stream
     * @throws Exception if video length is undefined
     */
    private ResourceRegion resourceRegion(UrlResource stream, HttpHeaders headers) throws Exception {
        long contentLength = stream.contentLength();

        // if header does not contain "Range" field, serve the entire video
        if (headers.getRange().isEmpty()) {
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
     * Returns a user's given stream name according to internal Limelight stream naming conventions.
     *
     * @param user owner of stream
     * @return file name of user's stream
     */
    private String getStreamFromUser(User user) {
        return String.format("%d.mp4", user.hashCode());
    }
}
package com.limelight.server;

import java.util.*;

/**
 * Timer for the each livestream session.
 */
public class StreamTimer {
  StreamQueue queue = StreamQueue.getInstance();
  // Timer to maintain seconds passed during livestream.
  Timer timer = new Timer();

  // Maximum amount of time a livestream can last
  private static long maximumLivestreamSeconds = 180;

  // Minimum amount of time a livestream can last
  private static long minimumLivestreamSeconds = 60;

  // The number of seconds the livestream has lasted already
  private long secondsPastDuringLivestream;

  // The number of seconds left that the livestream will last
  private long secondsLeftOfLivestream;

  // False if timer has expired.
  private boolean hasTimeLeft;

  /**
   * Constructor initializes new timer. secondHasPassed should be called every
   * second
   */
  public StreamTimer() {
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        secondHasPassed();
      }
    }, 0, 1000);
    secondsPastDuringLivestream = 0;
    secondsLeftOfLivestream = 60;
    hasTimeLeft = true;
  }

  /**
   * This function is called every second. Simulates one second passing for a
   * livestream.
   */
  public void secondHasPassed() {
    secondsPastDuringLivestream++;
    secondsLeftOfLivestream--;
    if (secondsLeftOfLivestream <= 0 && secondsPastDuringLivestream < minimumLivestreamSeconds) {
      secondsLeftOfLivestream = minimumLivestreamSeconds - secondsPastDuringLivestream;
    } else if (secondsLeftOfLivestream <= 0) {
      queue.pollStreamer();
      timer.cancel();
      timer.purge();
      hasTimeLeft = false;
    } else {
      // Do nothing
    }
  }

  /**
   * How long the livestream has already been running
   * 
   * @return seconds
   */
  public long getSecondsPastDuringLivestream() {
    return secondsPastDuringLivestream;
  }

  /**
   * How many more seconds the livestreamer can stream, unless cut off by the
   * maximumLivestreamSeconds cap.
   * 
   * @return seconds
   */
  public long getSecondsLeftOfLivestream() {
    return secondsLeftOfLivestream;
  }

  /**
   * Increase the number of seconds the livestreamer is allowed to stream, unless
   * cut off by the maximumLivestreamSeconds cap.
   * 
   * @param seconds amount of seconds to increase secondsLeftOfLivestream.
   */
  public void addSecondsToLivestream(long seconds) {
    secondsLeftOfLivestream += seconds;
  }

  /**
   * Decrease the number of seconds the livestreamer is allowed to stream.
   * 
   * @param seconds amount of seconds to decrease secondsLeftOfLivestream.
   */
  public void decreaseSecondsFromLivestream(long seconds) {
    secondsLeftOfLivestream -= seconds;
  }

  /**
   * Returns true if the timer has time left to run or false if the timer has
   * expired.
   * 
   * @return hasTimeLeft
   */
  public boolean getHasTimeLeft() {
    return hasTimeLeft;
  }
}
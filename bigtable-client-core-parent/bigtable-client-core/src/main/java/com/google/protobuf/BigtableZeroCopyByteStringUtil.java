package com.google.protobuf;


/**
 * Helper class to extract byte arrays from {@link com.google.protobuf.ByteString} without copy.
 * <p>
 * Without this protobufs would force us to copy every single byte array out of the objects
 * de-serialized from the wire (which already do one copy, on top of the copies the JVM does to go
 * from kernel buffer to C buffer and from C buffer to JVM buffer).
 *
 * @author sduskis
 * @version $Id: $Id
 */
public final class BigtableZeroCopyByteStringUtil {
  /**
   * Wraps a byte array in a {@link com.google.protobuf.ByteString} without copying it.
   *
   * @param array an array of byte.
   * @return a {@link com.google.protobuf.ByteString} object.
   */
  public static ByteString wrap(final byte[] array) {
    return new LiteralByteString(array);
  }

  /**
   * Wraps a subset of a byte array in a {@link com.google.protobuf.ByteString} without copying it.
   *
   * @param array an array of byte.
   * @param offset a int.
   * @param length a int.
   * @return a {@link com.google.protobuf.ByteString} object.
   */
  public static ByteString wrap(final byte[] array, int offset, int length) {
    return new BoundedByteString(array, offset, length);
  }

  /**
   * Extracts the byte array from the given {@link com.google.protobuf.ByteString} without copy.
   *
   * @param buf A buffer from which to extract the array. This buffer must be an instance of a
   *          {@code LiteralByteString} in order to be efficient. {@link com.google.protobuf.ByteString#toByteArray()}
   *          will be called All other implementations, including subclasses like
   *          {@link com.google.protobuf.BoundedByteString}
   * @return an array of byte.
   */
  public static byte[] zeroCopyGetBytes(final ByteString buf) {
    if (buf.getClass() == LiteralByteString.class) {
      return ((LiteralByteString) buf).bytes;
    } else {
      return buf.toByteArray();
    }
  }
}

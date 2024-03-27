// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: communication-protocol.proto

// Protobuf Java Version: 4.26.0
package networking;

/**
 * <pre>
 * Data structures
 * </pre>
 *
 * Protobuf type {@code main.ProcessId}
 */
public final class ProcessId extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:main.ProcessId)
    ProcessIdOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 0,
      /* suffix= */ "",
      ProcessId.class.getName());
  }
  // Use ProcessId.newBuilder() to construct.
  private ProcessId(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private ProcessId() {
    host_ = "";
    owner_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return networking.CommunicationProtocol.internal_static_main_ProcessId_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return networking.CommunicationProtocol.internal_static_main_ProcessId_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            networking.ProcessId.class, networking.ProcessId.Builder.class);
  }

  public static final int HOST_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object host_ = "";
  /**
   * <pre>
   * String containing either domain name  or IP (most likely)
   * </pre>
   *
   * <code>string host = 1;</code>
   * @return The host.
   */
  @java.lang.Override
  public java.lang.String getHost() {
    java.lang.Object ref = host_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      host_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * String containing either domain name  or IP (most likely)
   * </pre>
   *
   * <code>string host = 1;</code>
   * @return The bytes for host.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getHostBytes() {
    java.lang.Object ref = host_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      host_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PORT_FIELD_NUMBER = 2;
  private int port_ = 0;
  /**
   * <code>int32 port = 2;</code>
   * @return The port.
   */
  @java.lang.Override
  public int getPort() {
    return port_;
  }

  public static final int OWNER_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object owner_ = "";
  /**
   * <pre>
   * Short alias or acronym of the owner of the process
   * </pre>
   *
   * <code>string owner = 3;</code>
   * @return The owner.
   */
  @java.lang.Override
  public java.lang.String getOwner() {
    java.lang.Object ref = owner_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      owner_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Short alias or acronym of the owner of the process
   * </pre>
   *
   * <code>string owner = 3;</code>
   * @return The bytes for owner.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getOwnerBytes() {
    java.lang.Object ref = owner_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      owner_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int INDEX_FIELD_NUMBER = 4;
  private int index_ = 0;
  /**
   * <pre>
   * Process index within the owner's processes. Must be 1, 2, or 3
   * </pre>
   *
   * <code>int32 index = 4;</code>
   * @return The index.
   */
  @java.lang.Override
  public int getIndex() {
    return index_;
  }

  public static final int RANK_FIELD_NUMBER = 5;
  private int rank_ = 0;
  /**
   * <pre>
   * Populated by the hub when initiating consensus with AppPropose. Do not calculate your own rank,
   * </pre>
   *
   * <code>int32 rank = 5;</code>
   * @return The rank.
   */
  @java.lang.Override
  public int getRank() {
    return rank_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(host_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, host_);
    }
    if (port_ != 0) {
      output.writeInt32(2, port_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(owner_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, owner_);
    }
    if (index_ != 0) {
      output.writeInt32(4, index_);
    }
    if (rank_ != 0) {
      output.writeInt32(5, rank_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(host_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, host_);
    }
    if (port_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, port_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(owner_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, owner_);
    }
    if (index_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, index_);
    }
    if (rank_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, rank_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof networking.ProcessId)) {
      return super.equals(obj);
    }
    networking.ProcessId other = (networking.ProcessId) obj;

    if (!getHost()
        .equals(other.getHost())) return false;
    if (getPort()
        != other.getPort()) return false;
    if (!getOwner()
        .equals(other.getOwner())) return false;
    if (getIndex()
        != other.getIndex()) return false;
    if (getRank()
        != other.getRank()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + HOST_FIELD_NUMBER;
    hash = (53 * hash) + getHost().hashCode();
    hash = (37 * hash) + PORT_FIELD_NUMBER;
    hash = (53 * hash) + getPort();
    hash = (37 * hash) + OWNER_FIELD_NUMBER;
    hash = (53 * hash) + getOwner().hashCode();
    hash = (37 * hash) + INDEX_FIELD_NUMBER;
    hash = (53 * hash) + getIndex();
    hash = (37 * hash) + RANK_FIELD_NUMBER;
    hash = (53 * hash) + getRank();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static networking.ProcessId parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.ProcessId parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.ProcessId parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.ProcessId parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.ProcessId parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.ProcessId parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.ProcessId parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.ProcessId parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static networking.ProcessId parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static networking.ProcessId parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static networking.ProcessId parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.ProcessId parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(networking.ProcessId prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Data structures
   * </pre>
   *
   * Protobuf type {@code main.ProcessId}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:main.ProcessId)
      networking.ProcessIdOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return networking.CommunicationProtocol.internal_static_main_ProcessId_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return networking.CommunicationProtocol.internal_static_main_ProcessId_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              networking.ProcessId.class, networking.ProcessId.Builder.class);
    }

    // Construct using networking.ProcessId.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      host_ = "";
      port_ = 0;
      owner_ = "";
      index_ = 0;
      rank_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return networking.CommunicationProtocol.internal_static_main_ProcessId_descriptor;
    }

    @java.lang.Override
    public networking.ProcessId getDefaultInstanceForType() {
      return networking.ProcessId.getDefaultInstance();
    }

    @java.lang.Override
    public networking.ProcessId build() {
      networking.ProcessId result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public networking.ProcessId buildPartial() {
      networking.ProcessId result = new networking.ProcessId(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(networking.ProcessId result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.host_ = host_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.port_ = port_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.owner_ = owner_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.index_ = index_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.rank_ = rank_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof networking.ProcessId) {
        return mergeFrom((networking.ProcessId)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(networking.ProcessId other) {
      if (other == networking.ProcessId.getDefaultInstance()) return this;
      if (!other.getHost().isEmpty()) {
        host_ = other.host_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.getPort() != 0) {
        setPort(other.getPort());
      }
      if (!other.getOwner().isEmpty()) {
        owner_ = other.owner_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (other.getIndex() != 0) {
        setIndex(other.getIndex());
      }
      if (other.getRank() != 0) {
        setRank(other.getRank());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              host_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              port_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              owner_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 32: {
              index_ = input.readInt32();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
            case 40: {
              rank_ = input.readInt32();
              bitField0_ |= 0x00000010;
              break;
            } // case 40
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private java.lang.Object host_ = "";
    /**
     * <pre>
     * String containing either domain name  or IP (most likely)
     * </pre>
     *
     * <code>string host = 1;</code>
     * @return The host.
     */
    public java.lang.String getHost() {
      java.lang.Object ref = host_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        host_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * String containing either domain name  or IP (most likely)
     * </pre>
     *
     * <code>string host = 1;</code>
     * @return The bytes for host.
     */
    public com.google.protobuf.ByteString
        getHostBytes() {
      java.lang.Object ref = host_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        host_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * String containing either domain name  or IP (most likely)
     * </pre>
     *
     * <code>string host = 1;</code>
     * @param value The host to set.
     * @return This builder for chaining.
     */
    public Builder setHost(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      host_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * String containing either domain name  or IP (most likely)
     * </pre>
     *
     * <code>string host = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearHost() {
      host_ = getDefaultInstance().getHost();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * String containing either domain name  or IP (most likely)
     * </pre>
     *
     * <code>string host = 1;</code>
     * @param value The bytes for host to set.
     * @return This builder for chaining.
     */
    public Builder setHostBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      host_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private int port_ ;
    /**
     * <code>int32 port = 2;</code>
     * @return The port.
     */
    @java.lang.Override
    public int getPort() {
      return port_;
    }
    /**
     * <code>int32 port = 2;</code>
     * @param value The port to set.
     * @return This builder for chaining.
     */
    public Builder setPort(int value) {

      port_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>int32 port = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearPort() {
      bitField0_ = (bitField0_ & ~0x00000002);
      port_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object owner_ = "";
    /**
     * <pre>
     * Short alias or acronym of the owner of the process
     * </pre>
     *
     * <code>string owner = 3;</code>
     * @return The owner.
     */
    public java.lang.String getOwner() {
      java.lang.Object ref = owner_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        owner_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Short alias or acronym of the owner of the process
     * </pre>
     *
     * <code>string owner = 3;</code>
     * @return The bytes for owner.
     */
    public com.google.protobuf.ByteString
        getOwnerBytes() {
      java.lang.Object ref = owner_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        owner_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Short alias or acronym of the owner of the process
     * </pre>
     *
     * <code>string owner = 3;</code>
     * @param value The owner to set.
     * @return This builder for chaining.
     */
    public Builder setOwner(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      owner_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Short alias or acronym of the owner of the process
     * </pre>
     *
     * <code>string owner = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearOwner() {
      owner_ = getDefaultInstance().getOwner();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Short alias or acronym of the owner of the process
     * </pre>
     *
     * <code>string owner = 3;</code>
     * @param value The bytes for owner to set.
     * @return This builder for chaining.
     */
    public Builder setOwnerBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      owner_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private int index_ ;
    /**
     * <pre>
     * Process index within the owner's processes. Must be 1, 2, or 3
     * </pre>
     *
     * <code>int32 index = 4;</code>
     * @return The index.
     */
    @java.lang.Override
    public int getIndex() {
      return index_;
    }
    /**
     * <pre>
     * Process index within the owner's processes. Must be 1, 2, or 3
     * </pre>
     *
     * <code>int32 index = 4;</code>
     * @param value The index to set.
     * @return This builder for chaining.
     */
    public Builder setIndex(int value) {

      index_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Process index within the owner's processes. Must be 1, 2, or 3
     * </pre>
     *
     * <code>int32 index = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearIndex() {
      bitField0_ = (bitField0_ & ~0x00000008);
      index_ = 0;
      onChanged();
      return this;
    }

    private int rank_ ;
    /**
     * <pre>
     * Populated by the hub when initiating consensus with AppPropose. Do not calculate your own rank,
     * </pre>
     *
     * <code>int32 rank = 5;</code>
     * @return The rank.
     */
    @java.lang.Override
    public int getRank() {
      return rank_;
    }
    /**
     * <pre>
     * Populated by the hub when initiating consensus with AppPropose. Do not calculate your own rank,
     * </pre>
     *
     * <code>int32 rank = 5;</code>
     * @param value The rank to set.
     * @return This builder for chaining.
     */
    public Builder setRank(int value) {

      rank_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Populated by the hub when initiating consensus with AppPropose. Do not calculate your own rank,
     * </pre>
     *
     * <code>int32 rank = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearRank() {
      bitField0_ = (bitField0_ & ~0x00000010);
      rank_ = 0;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:main.ProcessId)
  }

  // @@protoc_insertion_point(class_scope:main.ProcessId)
  private static final networking.ProcessId DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new networking.ProcessId();
  }

  public static networking.ProcessId getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ProcessId>
      PARSER = new com.google.protobuf.AbstractParser<ProcessId>() {
    @java.lang.Override
    public ProcessId parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<ProcessId> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ProcessId> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public networking.ProcessId getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: communication-protocol.proto

// Protobuf Java Version: 4.26.0
package networking;

/**
 * Protobuf type {@code main.EcStartEpoch}
 */
public final class EcStartEpoch extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:main.EcStartEpoch)
    EcStartEpochOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 0,
      /* suffix= */ "",
      EcStartEpoch.class.getName());
  }
  // Use EcStartEpoch.newBuilder() to construct.
  private EcStartEpoch(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private EcStartEpoch() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return networking.CommunicationProtocol.internal_static_main_EcStartEpoch_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return networking.CommunicationProtocol.internal_static_main_EcStartEpoch_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            networking.EcStartEpoch.class, networking.EcStartEpoch.Builder.class);
  }

  private int bitField0_;
  public static final int NEWTIMESTAMP_FIELD_NUMBER = 1;
  private int newTimestamp_ = 0;
  /**
   * <code>int32 newTimestamp = 1;</code>
   * @return The newTimestamp.
   */
  @java.lang.Override
  public int getNewTimestamp() {
    return newTimestamp_;
  }

  public static final int NEWLEADER_FIELD_NUMBER = 2;
  private networking.ProcessId newLeader_;
  /**
   * <code>.main.ProcessId newLeader = 2;</code>
   * @return Whether the newLeader field is set.
   */
  @java.lang.Override
  public boolean hasNewLeader() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.main.ProcessId newLeader = 2;</code>
   * @return The newLeader.
   */
  @java.lang.Override
  public networking.ProcessId getNewLeader() {
    return newLeader_ == null ? networking.ProcessId.getDefaultInstance() : newLeader_;
  }
  /**
   * <code>.main.ProcessId newLeader = 2;</code>
   */
  @java.lang.Override
  public networking.ProcessIdOrBuilder getNewLeaderOrBuilder() {
    return newLeader_ == null ? networking.ProcessId.getDefaultInstance() : newLeader_;
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
    if (newTimestamp_ != 0) {
      output.writeInt32(1, newTimestamp_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(2, getNewLeader());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (newTimestamp_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, newTimestamp_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getNewLeader());
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
    if (!(obj instanceof networking.EcStartEpoch)) {
      return super.equals(obj);
    }
    networking.EcStartEpoch other = (networking.EcStartEpoch) obj;

    if (getNewTimestamp()
        != other.getNewTimestamp()) return false;
    if (hasNewLeader() != other.hasNewLeader()) return false;
    if (hasNewLeader()) {
      if (!getNewLeader()
          .equals(other.getNewLeader())) return false;
    }
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
    hash = (37 * hash) + NEWTIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + getNewTimestamp();
    if (hasNewLeader()) {
      hash = (37 * hash) + NEWLEADER_FIELD_NUMBER;
      hash = (53 * hash) + getNewLeader().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static networking.EcStartEpoch parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.EcStartEpoch parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.EcStartEpoch parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.EcStartEpoch parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.EcStartEpoch parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.EcStartEpoch parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.EcStartEpoch parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.EcStartEpoch parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static networking.EcStartEpoch parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static networking.EcStartEpoch parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static networking.EcStartEpoch parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.EcStartEpoch parseFrom(
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
  public static Builder newBuilder(networking.EcStartEpoch prototype) {
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
   * Protobuf type {@code main.EcStartEpoch}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:main.EcStartEpoch)
      networking.EcStartEpochOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return networking.CommunicationProtocol.internal_static_main_EcStartEpoch_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return networking.CommunicationProtocol.internal_static_main_EcStartEpoch_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              networking.EcStartEpoch.class, networking.EcStartEpoch.Builder.class);
    }

    // Construct using networking.EcStartEpoch.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage
              .alwaysUseFieldBuilders) {
        getNewLeaderFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      newTimestamp_ = 0;
      newLeader_ = null;
      if (newLeaderBuilder_ != null) {
        newLeaderBuilder_.dispose();
        newLeaderBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return networking.CommunicationProtocol.internal_static_main_EcStartEpoch_descriptor;
    }

    @java.lang.Override
    public networking.EcStartEpoch getDefaultInstanceForType() {
      return networking.EcStartEpoch.getDefaultInstance();
    }

    @java.lang.Override
    public networking.EcStartEpoch build() {
      networking.EcStartEpoch result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public networking.EcStartEpoch buildPartial() {
      networking.EcStartEpoch result = new networking.EcStartEpoch(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(networking.EcStartEpoch result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.newTimestamp_ = newTimestamp_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.newLeader_ = newLeaderBuilder_ == null
            ? newLeader_
            : newLeaderBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof networking.EcStartEpoch) {
        return mergeFrom((networking.EcStartEpoch)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(networking.EcStartEpoch other) {
      if (other == networking.EcStartEpoch.getDefaultInstance()) return this;
      if (other.getNewTimestamp() != 0) {
        setNewTimestamp(other.getNewTimestamp());
      }
      if (other.hasNewLeader()) {
        mergeNewLeader(other.getNewLeader());
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
            case 8: {
              newTimestamp_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 18: {
              input.readMessage(
                  getNewLeaderFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000002;
              break;
            } // case 18
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

    private int newTimestamp_ ;
    /**
     * <code>int32 newTimestamp = 1;</code>
     * @return The newTimestamp.
     */
    @java.lang.Override
    public int getNewTimestamp() {
      return newTimestamp_;
    }
    /**
     * <code>int32 newTimestamp = 1;</code>
     * @param value The newTimestamp to set.
     * @return This builder for chaining.
     */
    public Builder setNewTimestamp(int value) {

      newTimestamp_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>int32 newTimestamp = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearNewTimestamp() {
      bitField0_ = (bitField0_ & ~0x00000001);
      newTimestamp_ = 0;
      onChanged();
      return this;
    }

    private networking.ProcessId newLeader_;
    private com.google.protobuf.SingleFieldBuilder<
        networking.ProcessId, networking.ProcessId.Builder, networking.ProcessIdOrBuilder> newLeaderBuilder_;
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     * @return Whether the newLeader field is set.
     */
    public boolean hasNewLeader() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     * @return The newLeader.
     */
    public networking.ProcessId getNewLeader() {
      if (newLeaderBuilder_ == null) {
        return newLeader_ == null ? networking.ProcessId.getDefaultInstance() : newLeader_;
      } else {
        return newLeaderBuilder_.getMessage();
      }
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    public Builder setNewLeader(networking.ProcessId value) {
      if (newLeaderBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        newLeader_ = value;
      } else {
        newLeaderBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    public Builder setNewLeader(
        networking.ProcessId.Builder builderForValue) {
      if (newLeaderBuilder_ == null) {
        newLeader_ = builderForValue.build();
      } else {
        newLeaderBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    public Builder mergeNewLeader(networking.ProcessId value) {
      if (newLeaderBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0) &&
          newLeader_ != null &&
          newLeader_ != networking.ProcessId.getDefaultInstance()) {
          getNewLeaderBuilder().mergeFrom(value);
        } else {
          newLeader_ = value;
        }
      } else {
        newLeaderBuilder_.mergeFrom(value);
      }
      if (newLeader_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    public Builder clearNewLeader() {
      bitField0_ = (bitField0_ & ~0x00000002);
      newLeader_ = null;
      if (newLeaderBuilder_ != null) {
        newLeaderBuilder_.dispose();
        newLeaderBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    public networking.ProcessId.Builder getNewLeaderBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getNewLeaderFieldBuilder().getBuilder();
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    public networking.ProcessIdOrBuilder getNewLeaderOrBuilder() {
      if (newLeaderBuilder_ != null) {
        return newLeaderBuilder_.getMessageOrBuilder();
      } else {
        return newLeader_ == null ?
            networking.ProcessId.getDefaultInstance() : newLeader_;
      }
    }
    /**
     * <code>.main.ProcessId newLeader = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        networking.ProcessId, networking.ProcessId.Builder, networking.ProcessIdOrBuilder> 
        getNewLeaderFieldBuilder() {
      if (newLeaderBuilder_ == null) {
        newLeaderBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            networking.ProcessId, networking.ProcessId.Builder, networking.ProcessIdOrBuilder>(
                getNewLeader(),
                getParentForChildren(),
                isClean());
        newLeader_ = null;
      }
      return newLeaderBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:main.EcStartEpoch)
  }

  // @@protoc_insertion_point(class_scope:main.EcStartEpoch)
  private static final networking.EcStartEpoch DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new networking.EcStartEpoch();
  }

  public static networking.EcStartEpoch getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EcStartEpoch>
      PARSER = new com.google.protobuf.AbstractParser<EcStartEpoch>() {
    @java.lang.Override
    public EcStartEpoch parsePartialFrom(
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

  public static com.google.protobuf.Parser<EcStartEpoch> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<EcStartEpoch> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public networking.EcStartEpoch getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


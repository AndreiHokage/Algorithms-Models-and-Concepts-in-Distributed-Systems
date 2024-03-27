// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: communication-protocol.proto

// Protobuf Java Version: 4.26.0
package networking;

/**
 * Protobuf type {@code main.EpAborted}
 */
public final class EpAborted extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:main.EpAborted)
    EpAbortedOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 0,
      /* suffix= */ "",
      EpAborted.class.getName());
  }
  // Use EpAborted.newBuilder() to construct.
  private EpAborted(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private EpAborted() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return networking.CommunicationProtocol.internal_static_main_EpAborted_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return networking.CommunicationProtocol.internal_static_main_EpAborted_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            networking.EpAborted.class, networking.EpAborted.Builder.class);
  }

  private int bitField0_;
  public static final int ETS_FIELD_NUMBER = 1;
  private int ets_ = 0;
  /**
   * <pre>
   * Needed to know in UC the timestamp of the EP, where the algorithm says "such that ts = ets do"
   * </pre>
   *
   * <code>int32 ets = 1;</code>
   * @return The ets.
   */
  @java.lang.Override
  public int getEts() {
    return ets_;
  }

  public static final int VALUETIMESTAMP_FIELD_NUMBER = 2;
  private int valueTimestamp_ = 0;
  /**
   * <pre>
   * The timestamp part of the "state" mentioned in the algorithm
   * </pre>
   *
   * <code>int32 valueTimestamp = 2;</code>
   * @return The valueTimestamp.
   */
  @java.lang.Override
  public int getValueTimestamp() {
    return valueTimestamp_;
  }

  public static final int VALUE_FIELD_NUMBER = 3;
  private networking.Value value_;
  /**
   * <pre>
   * The value part of the "state" mentioned in the algorithm
   * </pre>
   *
   * <code>.main.Value value = 3;</code>
   * @return Whether the value field is set.
   */
  @java.lang.Override
  public boolean hasValue() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <pre>
   * The value part of the "state" mentioned in the algorithm
   * </pre>
   *
   * <code>.main.Value value = 3;</code>
   * @return The value.
   */
  @java.lang.Override
  public networking.Value getValue() {
    return value_ == null ? networking.Value.getDefaultInstance() : value_;
  }
  /**
   * <pre>
   * The value part of the "state" mentioned in the algorithm
   * </pre>
   *
   * <code>.main.Value value = 3;</code>
   */
  @java.lang.Override
  public networking.ValueOrBuilder getValueOrBuilder() {
    return value_ == null ? networking.Value.getDefaultInstance() : value_;
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
    if (ets_ != 0) {
      output.writeInt32(1, ets_);
    }
    if (valueTimestamp_ != 0) {
      output.writeInt32(2, valueTimestamp_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(3, getValue());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (ets_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, ets_);
    }
    if (valueTimestamp_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, valueTimestamp_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getValue());
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
    if (!(obj instanceof networking.EpAborted)) {
      return super.equals(obj);
    }
    networking.EpAborted other = (networking.EpAborted) obj;

    if (getEts()
        != other.getEts()) return false;
    if (getValueTimestamp()
        != other.getValueTimestamp()) return false;
    if (hasValue() != other.hasValue()) return false;
    if (hasValue()) {
      if (!getValue()
          .equals(other.getValue())) return false;
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
    hash = (37 * hash) + ETS_FIELD_NUMBER;
    hash = (53 * hash) + getEts();
    hash = (37 * hash) + VALUETIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + getValueTimestamp();
    if (hasValue()) {
      hash = (37 * hash) + VALUE_FIELD_NUMBER;
      hash = (53 * hash) + getValue().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static networking.EpAborted parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.EpAborted parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.EpAborted parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.EpAborted parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.EpAborted parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.EpAborted parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.EpAborted parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.EpAborted parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static networking.EpAborted parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static networking.EpAborted parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static networking.EpAborted parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.EpAborted parseFrom(
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
  public static Builder newBuilder(networking.EpAborted prototype) {
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
   * Protobuf type {@code main.EpAborted}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:main.EpAborted)
      networking.EpAbortedOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return networking.CommunicationProtocol.internal_static_main_EpAborted_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return networking.CommunicationProtocol.internal_static_main_EpAborted_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              networking.EpAborted.class, networking.EpAborted.Builder.class);
    }

    // Construct using networking.EpAborted.newBuilder()
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
        getValueFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      ets_ = 0;
      valueTimestamp_ = 0;
      value_ = null;
      if (valueBuilder_ != null) {
        valueBuilder_.dispose();
        valueBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return networking.CommunicationProtocol.internal_static_main_EpAborted_descriptor;
    }

    @java.lang.Override
    public networking.EpAborted getDefaultInstanceForType() {
      return networking.EpAborted.getDefaultInstance();
    }

    @java.lang.Override
    public networking.EpAborted build() {
      networking.EpAborted result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public networking.EpAborted buildPartial() {
      networking.EpAborted result = new networking.EpAborted(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(networking.EpAborted result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.ets_ = ets_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.valueTimestamp_ = valueTimestamp_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.value_ = valueBuilder_ == null
            ? value_
            : valueBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof networking.EpAborted) {
        return mergeFrom((networking.EpAborted)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(networking.EpAborted other) {
      if (other == networking.EpAborted.getDefaultInstance()) return this;
      if (other.getEts() != 0) {
        setEts(other.getEts());
      }
      if (other.getValueTimestamp() != 0) {
        setValueTimestamp(other.getValueTimestamp());
      }
      if (other.hasValue()) {
        mergeValue(other.getValue());
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
              ets_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              valueTimestamp_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              input.readMessage(
                  getValueFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000004;
              break;
            } // case 26
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

    private int ets_ ;
    /**
     * <pre>
     * Needed to know in UC the timestamp of the EP, where the algorithm says "such that ts = ets do"
     * </pre>
     *
     * <code>int32 ets = 1;</code>
     * @return The ets.
     */
    @java.lang.Override
    public int getEts() {
      return ets_;
    }
    /**
     * <pre>
     * Needed to know in UC the timestamp of the EP, where the algorithm says "such that ts = ets do"
     * </pre>
     *
     * <code>int32 ets = 1;</code>
     * @param value The ets to set.
     * @return This builder for chaining.
     */
    public Builder setEts(int value) {

      ets_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Needed to know in UC the timestamp of the EP, where the algorithm says "such that ts = ets do"
     * </pre>
     *
     * <code>int32 ets = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearEts() {
      bitField0_ = (bitField0_ & ~0x00000001);
      ets_ = 0;
      onChanged();
      return this;
    }

    private int valueTimestamp_ ;
    /**
     * <pre>
     * The timestamp part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>int32 valueTimestamp = 2;</code>
     * @return The valueTimestamp.
     */
    @java.lang.Override
    public int getValueTimestamp() {
      return valueTimestamp_;
    }
    /**
     * <pre>
     * The timestamp part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>int32 valueTimestamp = 2;</code>
     * @param value The valueTimestamp to set.
     * @return This builder for chaining.
     */
    public Builder setValueTimestamp(int value) {

      valueTimestamp_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The timestamp part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>int32 valueTimestamp = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearValueTimestamp() {
      bitField0_ = (bitField0_ & ~0x00000002);
      valueTimestamp_ = 0;
      onChanged();
      return this;
    }

    private networking.Value value_;
    private com.google.protobuf.SingleFieldBuilder<
        networking.Value, networking.Value.Builder, networking.ValueOrBuilder> valueBuilder_;
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     * @return Whether the value field is set.
     */
    public boolean hasValue() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     * @return The value.
     */
    public networking.Value getValue() {
      if (valueBuilder_ == null) {
        return value_ == null ? networking.Value.getDefaultInstance() : value_;
      } else {
        return valueBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    public Builder setValue(networking.Value value) {
      if (valueBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        value_ = value;
      } else {
        valueBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    public Builder setValue(
        networking.Value.Builder builderForValue) {
      if (valueBuilder_ == null) {
        value_ = builderForValue.build();
      } else {
        valueBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    public Builder mergeValue(networking.Value value) {
      if (valueBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0) &&
          value_ != null &&
          value_ != networking.Value.getDefaultInstance()) {
          getValueBuilder().mergeFrom(value);
        } else {
          value_ = value;
        }
      } else {
        valueBuilder_.mergeFrom(value);
      }
      if (value_ != null) {
        bitField0_ |= 0x00000004;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    public Builder clearValue() {
      bitField0_ = (bitField0_ & ~0x00000004);
      value_ = null;
      if (valueBuilder_ != null) {
        valueBuilder_.dispose();
        valueBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    public networking.Value.Builder getValueBuilder() {
      bitField0_ |= 0x00000004;
      onChanged();
      return getValueFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    public networking.ValueOrBuilder getValueOrBuilder() {
      if (valueBuilder_ != null) {
        return valueBuilder_.getMessageOrBuilder();
      } else {
        return value_ == null ?
            networking.Value.getDefaultInstance() : value_;
      }
    }
    /**
     * <pre>
     * The value part of the "state" mentioned in the algorithm
     * </pre>
     *
     * <code>.main.Value value = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        networking.Value, networking.Value.Builder, networking.ValueOrBuilder> 
        getValueFieldBuilder() {
      if (valueBuilder_ == null) {
        valueBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            networking.Value, networking.Value.Builder, networking.ValueOrBuilder>(
                getValue(),
                getParentForChildren(),
                isClean());
        value_ = null;
      }
      return valueBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:main.EpAborted)
  }

  // @@protoc_insertion_point(class_scope:main.EpAborted)
  private static final networking.EpAborted DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new networking.EpAborted();
  }

  public static networking.EpAborted getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EpAborted>
      PARSER = new com.google.protobuf.AbstractParser<EpAborted>() {
    @java.lang.Override
    public EpAborted parsePartialFrom(
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

  public static com.google.protobuf.Parser<EpAborted> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<EpAborted> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public networking.EpAborted getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

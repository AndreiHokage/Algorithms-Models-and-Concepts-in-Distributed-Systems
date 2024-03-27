// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: communication-protocol.proto

// Protobuf Java Version: 4.26.0
package networking;

/**
 * <pre>
 * Received from the HUB as Message(NetworkMessage(Message(AppRead)))
 * </pre>
 *
 * Protobuf type {@code main.AppRead}
 */
public final class AppRead extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:main.AppRead)
    AppReadOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 0,
      /* suffix= */ "",
      AppRead.class.getName());
  }
  // Use AppRead.newBuilder() to construct.
  private AppRead(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private AppRead() {
    register_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return networking.CommunicationProtocol.internal_static_main_AppRead_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return networking.CommunicationProtocol.internal_static_main_AppRead_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            networking.AppRead.class, networking.AppRead.Builder.class);
  }

  public static final int REGISTER_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object register_ = "";
  /**
   * <code>string register = 1;</code>
   * @return The register.
   */
  @java.lang.Override
  public java.lang.String getRegister() {
    java.lang.Object ref = register_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      register_ = s;
      return s;
    }
  }
  /**
   * <code>string register = 1;</code>
   * @return The bytes for register.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getRegisterBytes() {
    java.lang.Object ref = register_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      register_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(register_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, register_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(register_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, register_);
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
    if (!(obj instanceof networking.AppRead)) {
      return super.equals(obj);
    }
    networking.AppRead other = (networking.AppRead) obj;

    if (!getRegister()
        .equals(other.getRegister())) return false;
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
    hash = (37 * hash) + REGISTER_FIELD_NUMBER;
    hash = (53 * hash) + getRegister().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static networking.AppRead parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.AppRead parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.AppRead parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.AppRead parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.AppRead parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static networking.AppRead parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static networking.AppRead parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.AppRead parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static networking.AppRead parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static networking.AppRead parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static networking.AppRead parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static networking.AppRead parseFrom(
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
  public static Builder newBuilder(networking.AppRead prototype) {
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
   * Received from the HUB as Message(NetworkMessage(Message(AppRead)))
   * </pre>
   *
   * Protobuf type {@code main.AppRead}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:main.AppRead)
      networking.AppReadOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return networking.CommunicationProtocol.internal_static_main_AppRead_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return networking.CommunicationProtocol.internal_static_main_AppRead_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              networking.AppRead.class, networking.AppRead.Builder.class);
    }

    // Construct using networking.AppRead.newBuilder()
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
      register_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return networking.CommunicationProtocol.internal_static_main_AppRead_descriptor;
    }

    @java.lang.Override
    public networking.AppRead getDefaultInstanceForType() {
      return networking.AppRead.getDefaultInstance();
    }

    @java.lang.Override
    public networking.AppRead build() {
      networking.AppRead result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public networking.AppRead buildPartial() {
      networking.AppRead result = new networking.AppRead(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(networking.AppRead result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.register_ = register_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof networking.AppRead) {
        return mergeFrom((networking.AppRead)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(networking.AppRead other) {
      if (other == networking.AppRead.getDefaultInstance()) return this;
      if (!other.getRegister().isEmpty()) {
        register_ = other.register_;
        bitField0_ |= 0x00000001;
        onChanged();
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
              register_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
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

    private java.lang.Object register_ = "";
    /**
     * <code>string register = 1;</code>
     * @return The register.
     */
    public java.lang.String getRegister() {
      java.lang.Object ref = register_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        register_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string register = 1;</code>
     * @return The bytes for register.
     */
    public com.google.protobuf.ByteString
        getRegisterBytes() {
      java.lang.Object ref = register_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        register_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string register = 1;</code>
     * @param value The register to set.
     * @return This builder for chaining.
     */
    public Builder setRegister(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      register_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>string register = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRegister() {
      register_ = getDefaultInstance().getRegister();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>string register = 1;</code>
     * @param value The bytes for register to set.
     * @return This builder for chaining.
     */
    public Builder setRegisterBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      register_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:main.AppRead)
  }

  // @@protoc_insertion_point(class_scope:main.AppRead)
  private static final networking.AppRead DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new networking.AppRead();
  }

  public static networking.AppRead getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AppRead>
      PARSER = new com.google.protobuf.AbstractParser<AppRead>() {
    @java.lang.Override
    public AppRead parsePartialFrom(
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

  public static com.google.protobuf.Parser<AppRead> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AppRead> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public networking.AppRead getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: communication-protocol.proto

// Protobuf Java Version: 4.26.0
package networking;

public interface EcStartEpochOrBuilder extends
    // @@protoc_insertion_point(interface_extends:main.EcStartEpoch)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 newTimestamp = 1;</code>
   * @return The newTimestamp.
   */
  int getNewTimestamp();

  /**
   * <code>.main.ProcessId newLeader = 2;</code>
   * @return Whether the newLeader field is set.
   */
  boolean hasNewLeader();
  /**
   * <code>.main.ProcessId newLeader = 2;</code>
   * @return The newLeader.
   */
  networking.ProcessId getNewLeader();
  /**
   * <code>.main.ProcessId newLeader = 2;</code>
   */
  networking.ProcessIdOrBuilder getNewLeaderOrBuilder();
}
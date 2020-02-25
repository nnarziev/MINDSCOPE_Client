package inha.nsl.easytrack;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.18.0)",
    comments = "Source: et_service.proto")
public final class ETServiceGrpc {

  private ETServiceGrpc() {}

  public static final String SERVICE_NAME = "ETService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage,
      inha.nsl.easytrack.EtService.LoginResponseMessage> getLoginWithGoogleIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "loginWithGoogleId",
      requestType = inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.LoginResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage,
      inha.nsl.easytrack.EtService.LoginResponseMessage> getLoginWithGoogleIdMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage, inha.nsl.easytrack.EtService.LoginResponseMessage> getLoginWithGoogleIdMethod;
    if ((getLoginWithGoogleIdMethod = ETServiceGrpc.getLoginWithGoogleIdMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getLoginWithGoogleIdMethod = ETServiceGrpc.getLoginWithGoogleIdMethod) == null) {
          ETServiceGrpc.getLoginWithGoogleIdMethod = getLoginWithGoogleIdMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage, inha.nsl.easytrack.EtService.LoginResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "loginWithGoogleId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.LoginResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getLoginWithGoogleIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage,
      inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage> getBindUserToCampaignMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bindUserToCampaign",
      requestType = inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage,
      inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage> getBindUserToCampaignMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage, inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage> getBindUserToCampaignMethod;
    if ((getBindUserToCampaignMethod = ETServiceGrpc.getBindUserToCampaignMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getBindUserToCampaignMethod = ETServiceGrpc.getBindUserToCampaignMethod) == null) {
          ETServiceGrpc.getBindUserToCampaignMethod = getBindUserToCampaignMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage, inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "bindUserToCampaign"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getBindUserToCampaignMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage,
      inha.nsl.easytrack.EtService.LoginResponseMessage> getDashboardLoginWithEmailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "dashboardLoginWithEmail",
      requestType = inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.LoginResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage,
      inha.nsl.easytrack.EtService.LoginResponseMessage> getDashboardLoginWithEmailMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage, inha.nsl.easytrack.EtService.LoginResponseMessage> getDashboardLoginWithEmailMethod;
    if ((getDashboardLoginWithEmailMethod = ETServiceGrpc.getDashboardLoginWithEmailMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getDashboardLoginWithEmailMethod = ETServiceGrpc.getDashboardLoginWithEmailMethod) == null) {
          ETServiceGrpc.getDashboardLoginWithEmailMethod = getDashboardLoginWithEmailMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage, inha.nsl.easytrack.EtService.LoginResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "dashboardLoginWithEmail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.LoginResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getDashboardLoginWithEmailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage,
      inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage> getRegisterCampaignMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerCampaign",
      requestType = inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage,
      inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage> getRegisterCampaignMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage, inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage> getRegisterCampaignMethod;
    if ((getRegisterCampaignMethod = ETServiceGrpc.getRegisterCampaignMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRegisterCampaignMethod = ETServiceGrpc.getRegisterCampaignMethod) == null) {
          ETServiceGrpc.getRegisterCampaignMethod = getRegisterCampaignMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage, inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "registerCampaign"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRegisterCampaignMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getDeleteCampaignMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteCampaign",
      requestType = inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.DefaultResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getDeleteCampaignMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage> getDeleteCampaignMethod;
    if ((getDeleteCampaignMethod = ETServiceGrpc.getDeleteCampaignMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getDeleteCampaignMethod = ETServiceGrpc.getDeleteCampaignMethod) == null) {
          ETServiceGrpc.getDeleteCampaignMethod = getDeleteCampaignMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "deleteCampaign"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.DefaultResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getDeleteCampaignMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage> getRetrieveCampaignsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveCampaigns",
      requestType = inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage> getRetrieveCampaignsMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage, inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage> getRetrieveCampaignsMethod;
    if ((getRetrieveCampaignsMethod = ETServiceGrpc.getRetrieveCampaignsMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveCampaignsMethod = ETServiceGrpc.getRetrieveCampaignsMethod) == null) {
          ETServiceGrpc.getRetrieveCampaignsMethod = getRetrieveCampaignsMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage, inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveCampaigns"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveCampaignsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage> getRetrieveCampaignMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveCampaign",
      requestType = inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage> getRetrieveCampaignMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage, inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage> getRetrieveCampaignMethod;
    if ((getRetrieveCampaignMethod = ETServiceGrpc.getRetrieveCampaignMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveCampaignMethod = ETServiceGrpc.getRetrieveCampaignMethod) == null) {
          ETServiceGrpc.getRetrieveCampaignMethod = getRetrieveCampaignMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage, inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveCampaign"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveCampaignMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitDataRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "submitDataRecord",
      requestType = inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.DefaultResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitDataRecordMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitDataRecordMethod;
    if ((getSubmitDataRecordMethod = ETServiceGrpc.getSubmitDataRecordMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getSubmitDataRecordMethod = ETServiceGrpc.getSubmitDataRecordMethod) == null) {
          ETServiceGrpc.getSubmitDataRecordMethod = getSubmitDataRecordMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "submitDataRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.DefaultResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getSubmitDataRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "submitHeartbeat",
      requestType = inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.DefaultResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitHeartbeatMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitHeartbeatMethod;
    if ((getSubmitHeartbeatMethod = ETServiceGrpc.getSubmitHeartbeatMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getSubmitHeartbeatMethod = ETServiceGrpc.getSubmitHeartbeatMethod) == null) {
          ETServiceGrpc.getSubmitHeartbeatMethod = getSubmitHeartbeatMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "submitHeartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.DefaultResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getSubmitHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitDirectMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "submitDirectMessage",
      requestType = inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.DefaultResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage,
      inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitDirectMessageMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage> getSubmitDirectMessageMethod;
    if ((getSubmitDirectMessageMethod = ETServiceGrpc.getSubmitDirectMessageMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getSubmitDirectMessageMethod = ETServiceGrpc.getSubmitDirectMessageMethod) == null) {
          ETServiceGrpc.getSubmitDirectMessageMethod = getSubmitDirectMessageMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage, inha.nsl.easytrack.EtService.DefaultResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "submitDirectMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.DefaultResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getSubmitDirectMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage> getRetrieveParticipantsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveParticipants",
      requestType = inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage> getRetrieveParticipantsMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage, inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage> getRetrieveParticipantsMethod;
    if ((getRetrieveParticipantsMethod = ETServiceGrpc.getRetrieveParticipantsMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveParticipantsMethod = ETServiceGrpc.getRetrieveParticipantsMethod) == null) {
          ETServiceGrpc.getRetrieveParticipantsMethod = getRetrieveParticipantsMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage, inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveParticipants"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveParticipantsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage> getRetrieveParticipantStatisticsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveParticipantStatistics",
      requestType = inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage> getRetrieveParticipantStatisticsMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage, inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage> getRetrieveParticipantStatisticsMethod;
    if ((getRetrieveParticipantStatisticsMethod = ETServiceGrpc.getRetrieveParticipantStatisticsMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveParticipantStatisticsMethod = ETServiceGrpc.getRetrieveParticipantStatisticsMethod) == null) {
          ETServiceGrpc.getRetrieveParticipantStatisticsMethod = getRetrieveParticipantStatisticsMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage, inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveParticipantStatistics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveParticipantStatisticsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage,
      inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage> getRetrieve100DataRecordsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieve100DataRecords",
      requestType = inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage,
      inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage> getRetrieve100DataRecordsMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage, inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage> getRetrieve100DataRecordsMethod;
    if ((getRetrieve100DataRecordsMethod = ETServiceGrpc.getRetrieve100DataRecordsMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieve100DataRecordsMethod = ETServiceGrpc.getRetrieve100DataRecordsMethod) == null) {
          ETServiceGrpc.getRetrieve100DataRecordsMethod = getRetrieve100DataRecordsMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage, inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieve100DataRecords"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieve100DataRecordsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage> getRetrieveFilteredDataRecordsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveFilteredDataRecords",
      requestType = inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage> getRetrieveFilteredDataRecordsMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage, inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage> getRetrieveFilteredDataRecordsMethod;
    if ((getRetrieveFilteredDataRecordsMethod = ETServiceGrpc.getRetrieveFilteredDataRecordsMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveFilteredDataRecordsMethod = ETServiceGrpc.getRetrieveFilteredDataRecordsMethod) == null) {
          ETServiceGrpc.getRetrieveFilteredDataRecordsMethod = getRetrieveFilteredDataRecordsMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage, inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveFilteredDataRecords"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveFilteredDataRecordsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage> getRetrieveUnreadDirectMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveUnreadDirectMessages",
      requestType = inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage> getRetrieveUnreadDirectMessagesMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage, inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage> getRetrieveUnreadDirectMessagesMethod;
    if ((getRetrieveUnreadDirectMessagesMethod = ETServiceGrpc.getRetrieveUnreadDirectMessagesMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveUnreadDirectMessagesMethod = ETServiceGrpc.getRetrieveUnreadDirectMessagesMethod) == null) {
          ETServiceGrpc.getRetrieveUnreadDirectMessagesMethod = getRetrieveUnreadDirectMessagesMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage, inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveUnreadDirectMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveUnreadDirectMessagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage> getRetrieveUnreadNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveUnreadNotifications",
      requestType = inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage> getRetrieveUnreadNotificationsMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage, inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage> getRetrieveUnreadNotificationsMethod;
    if ((getRetrieveUnreadNotificationsMethod = ETServiceGrpc.getRetrieveUnreadNotificationsMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveUnreadNotificationsMethod = ETServiceGrpc.getRetrieveUnreadNotificationsMethod) == null) {
          ETServiceGrpc.getRetrieveUnreadNotificationsMethod = getRetrieveUnreadNotificationsMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage, inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveUnreadNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveUnreadNotificationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.BindDataSourceRequestMessage,
      inha.nsl.easytrack.EtService.BindDataSourceResponseMessage> getBindDataSourceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bindDataSource",
      requestType = inha.nsl.easytrack.EtService.BindDataSourceRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.BindDataSourceResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.BindDataSourceRequestMessage,
      inha.nsl.easytrack.EtService.BindDataSourceResponseMessage> getBindDataSourceMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.BindDataSourceRequestMessage, inha.nsl.easytrack.EtService.BindDataSourceResponseMessage> getBindDataSourceMethod;
    if ((getBindDataSourceMethod = ETServiceGrpc.getBindDataSourceMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getBindDataSourceMethod = ETServiceGrpc.getBindDataSourceMethod) == null) {
          ETServiceGrpc.getBindDataSourceMethod = getBindDataSourceMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.BindDataSourceRequestMessage, inha.nsl.easytrack.EtService.BindDataSourceResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "bindDataSource"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.BindDataSourceRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.BindDataSourceResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getBindDataSourceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage> getRetrieveAllDataSourcesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "retrieveAllDataSources",
      requestType = inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage.class,
      responseType = inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage,
      inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage> getRetrieveAllDataSourcesMethod() {
    io.grpc.MethodDescriptor<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage, inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage> getRetrieveAllDataSourcesMethod;
    if ((getRetrieveAllDataSourcesMethod = ETServiceGrpc.getRetrieveAllDataSourcesMethod) == null) {
      synchronized (ETServiceGrpc.class) {
        if ((getRetrieveAllDataSourcesMethod = ETServiceGrpc.getRetrieveAllDataSourcesMethod) == null) {
          ETServiceGrpc.getRetrieveAllDataSourcesMethod = getRetrieveAllDataSourcesMethod = 
              io.grpc.MethodDescriptor.<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage, inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "ETService", "retrieveAllDataSources"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRetrieveAllDataSourcesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ETServiceStub newStub(io.grpc.Channel channel) {
    return new ETServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ETServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ETServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ETServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ETServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ETServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void loginWithGoogleId(inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.LoginResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getLoginWithGoogleIdMethod(), responseObserver);
    }

    /**
     */
    public void bindUserToCampaign(inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getBindUserToCampaignMethod(), responseObserver);
    }

    /**
     */
    public void dashboardLoginWithEmail(inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.LoginResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getDashboardLoginWithEmailMethod(), responseObserver);
    }

    /**
     */
    public void registerCampaign(inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterCampaignMethod(), responseObserver);
    }

    /**
     */
    public void deleteCampaign(inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteCampaignMethod(), responseObserver);
    }

    /**
     */
    public void retrieveCampaigns(inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveCampaignsMethod(), responseObserver);
    }

    /**
     */
    public void retrieveCampaign(inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveCampaignMethod(), responseObserver);
    }

    /**
     */
    public void submitDataRecord(inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getSubmitDataRecordMethod(), responseObserver);
    }

    /**
     */
    public void submitHeartbeat(inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getSubmitHeartbeatMethod(), responseObserver);
    }

    /**
     */
    public void submitDirectMessage(inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getSubmitDirectMessageMethod(), responseObserver);
    }

    /**
     */
    public void retrieveParticipants(inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveParticipantsMethod(), responseObserver);
    }

    /**
     */
    public void retrieveParticipantStatistics(inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveParticipantStatisticsMethod(), responseObserver);
    }

    /**
     */
    public void retrieve100DataRecords(inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieve100DataRecordsMethod(), responseObserver);
    }

    /**
     */
    public void retrieveFilteredDataRecords(inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveFilteredDataRecordsMethod(), responseObserver);
    }

    /**
     */
    public void retrieveUnreadDirectMessages(inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveUnreadDirectMessagesMethod(), responseObserver);
    }

    /**
     */
    public void retrieveUnreadNotifications(inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveUnreadNotificationsMethod(), responseObserver);
    }

    /**
     */
    public void bindDataSource(inha.nsl.easytrack.EtService.BindDataSourceRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.BindDataSourceResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getBindDataSourceMethod(), responseObserver);
    }

    /**
     */
    public void retrieveAllDataSources(inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveAllDataSourcesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginWithGoogleIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage,
                inha.nsl.easytrack.EtService.LoginResponseMessage>(
                  this, METHODID_LOGIN_WITH_GOOGLE_ID)))
          .addMethod(
            getBindUserToCampaignMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage,
                inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage>(
                  this, METHODID_BIND_USER_TO_CAMPAIGN)))
          .addMethod(
            getDashboardLoginWithEmailMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage,
                inha.nsl.easytrack.EtService.LoginResponseMessage>(
                  this, METHODID_DASHBOARD_LOGIN_WITH_EMAIL)))
          .addMethod(
            getRegisterCampaignMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage,
                inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage>(
                  this, METHODID_REGISTER_CAMPAIGN)))
          .addMethod(
            getDeleteCampaignMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage,
                inha.nsl.easytrack.EtService.DefaultResponseMessage>(
                  this, METHODID_DELETE_CAMPAIGN)))
          .addMethod(
            getRetrieveCampaignsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage>(
                  this, METHODID_RETRIEVE_CAMPAIGNS)))
          .addMethod(
            getRetrieveCampaignMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage>(
                  this, METHODID_RETRIEVE_CAMPAIGN)))
          .addMethod(
            getSubmitDataRecordMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage,
                inha.nsl.easytrack.EtService.DefaultResponseMessage>(
                  this, METHODID_SUBMIT_DATA_RECORD)))
          .addMethod(
            getSubmitHeartbeatMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage,
                inha.nsl.easytrack.EtService.DefaultResponseMessage>(
                  this, METHODID_SUBMIT_HEARTBEAT)))
          .addMethod(
            getSubmitDirectMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage,
                inha.nsl.easytrack.EtService.DefaultResponseMessage>(
                  this, METHODID_SUBMIT_DIRECT_MESSAGE)))
          .addMethod(
            getRetrieveParticipantsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage>(
                  this, METHODID_RETRIEVE_PARTICIPANTS)))
          .addMethod(
            getRetrieveParticipantStatisticsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage>(
                  this, METHODID_RETRIEVE_PARTICIPANT_STATISTICS)))
          .addMethod(
            getRetrieve100DataRecordsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage,
                inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage>(
                  this, METHODID_RETRIEVE100DATA_RECORDS)))
          .addMethod(
            getRetrieveFilteredDataRecordsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage>(
                  this, METHODID_RETRIEVE_FILTERED_DATA_RECORDS)))
          .addMethod(
            getRetrieveUnreadDirectMessagesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage>(
                  this, METHODID_RETRIEVE_UNREAD_DIRECT_MESSAGES)))
          .addMethod(
            getRetrieveUnreadNotificationsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage>(
                  this, METHODID_RETRIEVE_UNREAD_NOTIFICATIONS)))
          .addMethod(
            getBindDataSourceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.BindDataSourceRequestMessage,
                inha.nsl.easytrack.EtService.BindDataSourceResponseMessage>(
                  this, METHODID_BIND_DATA_SOURCE)))
          .addMethod(
            getRetrieveAllDataSourcesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage,
                inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage>(
                  this, METHODID_RETRIEVE_ALL_DATA_SOURCES)))
          .build();
    }
  }

  /**
   */
  public static final class ETServiceStub extends io.grpc.stub.AbstractStub<ETServiceStub> {
    private ETServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ETServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ETServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ETServiceStub(channel, callOptions);
    }

    /**
     */
    public void loginWithGoogleId(inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.LoginResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLoginWithGoogleIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void bindUserToCampaign(inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBindUserToCampaignMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void dashboardLoginWithEmail(inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.LoginResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDashboardLoginWithEmailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerCampaign(inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterCampaignMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteCampaign(inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteCampaignMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveCampaigns(inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveCampaignsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveCampaign(inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveCampaignMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitDataRecord(inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubmitDataRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitHeartbeat(inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubmitHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitDirectMessage(inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubmitDirectMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveParticipants(inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveParticipantsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveParticipantStatistics(inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveParticipantStatisticsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieve100DataRecords(inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieve100DataRecordsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveFilteredDataRecords(inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveFilteredDataRecordsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveUnreadDirectMessages(inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveUnreadDirectMessagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveUnreadNotifications(inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveUnreadNotificationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void bindDataSource(inha.nsl.easytrack.EtService.BindDataSourceRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.BindDataSourceResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBindDataSourceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieveAllDataSources(inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage request,
        io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveAllDataSourcesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ETServiceBlockingStub extends io.grpc.stub.AbstractStub<ETServiceBlockingStub> {
    private ETServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ETServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ETServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ETServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.LoginResponseMessage loginWithGoogleId(inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getLoginWithGoogleIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage bindUserToCampaign(inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getBindUserToCampaignMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.LoginResponseMessage dashboardLoginWithEmail(inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getDashboardLoginWithEmailMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage registerCampaign(inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRegisterCampaignMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.DefaultResponseMessage deleteCampaign(inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getDeleteCampaignMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage retrieveCampaigns(inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveCampaignsMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage retrieveCampaign(inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveCampaignMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.DefaultResponseMessage submitDataRecord(inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getSubmitDataRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.DefaultResponseMessage submitHeartbeat(inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getSubmitHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.DefaultResponseMessage submitDirectMessage(inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getSubmitDirectMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage retrieveParticipants(inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveParticipantsMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage retrieveParticipantStatistics(inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveParticipantStatisticsMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage retrieve100DataRecords(inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieve100DataRecordsMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage retrieveFilteredDataRecords(inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveFilteredDataRecordsMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage retrieveUnreadDirectMessages(inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveUnreadDirectMessagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage retrieveUnreadNotifications(inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveUnreadNotificationsMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.BindDataSourceResponseMessage bindDataSource(inha.nsl.easytrack.EtService.BindDataSourceRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getBindDataSourceMethod(), getCallOptions(), request);
    }

    /**
     */
    public inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage retrieveAllDataSources(inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveAllDataSourcesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ETServiceFutureStub extends io.grpc.stub.AbstractStub<ETServiceFutureStub> {
    private ETServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ETServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ETServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ETServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.LoginResponseMessage> loginWithGoogleId(
        inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getLoginWithGoogleIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage> bindUserToCampaign(
        inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getBindUserToCampaignMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.LoginResponseMessage> dashboardLoginWithEmail(
        inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getDashboardLoginWithEmailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage> registerCampaign(
        inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterCampaignMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.DefaultResponseMessage> deleteCampaign(
        inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteCampaignMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage> retrieveCampaigns(
        inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveCampaignsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage> retrieveCampaign(
        inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveCampaignMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.DefaultResponseMessage> submitDataRecord(
        inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getSubmitDataRecordMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.DefaultResponseMessage> submitHeartbeat(
        inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getSubmitHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.DefaultResponseMessage> submitDirectMessage(
        inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getSubmitDirectMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage> retrieveParticipants(
        inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveParticipantsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage> retrieveParticipantStatistics(
        inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveParticipantStatisticsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage> retrieve100DataRecords(
        inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieve100DataRecordsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage> retrieveFilteredDataRecords(
        inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveFilteredDataRecordsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage> retrieveUnreadDirectMessages(
        inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveUnreadDirectMessagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage> retrieveUnreadNotifications(
        inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveUnreadNotificationsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.BindDataSourceResponseMessage> bindDataSource(
        inha.nsl.easytrack.EtService.BindDataSourceRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getBindDataSourceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage> retrieveAllDataSources(
        inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveAllDataSourcesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN_WITH_GOOGLE_ID = 0;
  private static final int METHODID_BIND_USER_TO_CAMPAIGN = 1;
  private static final int METHODID_DASHBOARD_LOGIN_WITH_EMAIL = 2;
  private static final int METHODID_REGISTER_CAMPAIGN = 3;
  private static final int METHODID_DELETE_CAMPAIGN = 4;
  private static final int METHODID_RETRIEVE_CAMPAIGNS = 5;
  private static final int METHODID_RETRIEVE_CAMPAIGN = 6;
  private static final int METHODID_SUBMIT_DATA_RECORD = 7;
  private static final int METHODID_SUBMIT_HEARTBEAT = 8;
  private static final int METHODID_SUBMIT_DIRECT_MESSAGE = 9;
  private static final int METHODID_RETRIEVE_PARTICIPANTS = 10;
  private static final int METHODID_RETRIEVE_PARTICIPANT_STATISTICS = 11;
  private static final int METHODID_RETRIEVE100DATA_RECORDS = 12;
  private static final int METHODID_RETRIEVE_FILTERED_DATA_RECORDS = 13;
  private static final int METHODID_RETRIEVE_UNREAD_DIRECT_MESSAGES = 14;
  private static final int METHODID_RETRIEVE_UNREAD_NOTIFICATIONS = 15;
  private static final int METHODID_BIND_DATA_SOURCE = 16;
  private static final int METHODID_RETRIEVE_ALL_DATA_SOURCES = 17;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ETServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ETServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN_WITH_GOOGLE_ID:
          serviceImpl.loginWithGoogleId((inha.nsl.easytrack.EtService.LoginWithGoogleIdTokenRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.LoginResponseMessage>) responseObserver);
          break;
        case METHODID_BIND_USER_TO_CAMPAIGN:
          serviceImpl.bindUserToCampaign((inha.nsl.easytrack.EtService.BindUserToCampaignRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.BindUserToCampaignResponseMessage>) responseObserver);
          break;
        case METHODID_DASHBOARD_LOGIN_WITH_EMAIL:
          serviceImpl.dashboardLoginWithEmail((inha.nsl.easytrack.EtService.DashboardLoginWithEmailRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.LoginResponseMessage>) responseObserver);
          break;
        case METHODID_REGISTER_CAMPAIGN:
          serviceImpl.registerCampaign((inha.nsl.easytrack.EtService.RegisterCampaignRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RegisterCampaignResponseMessage>) responseObserver);
          break;
        case METHODID_DELETE_CAMPAIGN:
          serviceImpl.deleteCampaign((inha.nsl.easytrack.EtService.DeleteCampaignRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_CAMPAIGNS:
          serviceImpl.retrieveCampaigns((inha.nsl.easytrack.EtService.RetrieveCampaignsRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveCampaignsResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_CAMPAIGN:
          serviceImpl.retrieveCampaign((inha.nsl.easytrack.EtService.RetrieveCampaignRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveCampaignResponseMessage>) responseObserver);
          break;
        case METHODID_SUBMIT_DATA_RECORD:
          serviceImpl.submitDataRecord((inha.nsl.easytrack.EtService.SubmitDataRecordRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage>) responseObserver);
          break;
        case METHODID_SUBMIT_HEARTBEAT:
          serviceImpl.submitHeartbeat((inha.nsl.easytrack.EtService.SubmitHeartbeatRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage>) responseObserver);
          break;
        case METHODID_SUBMIT_DIRECT_MESSAGE:
          serviceImpl.submitDirectMessage((inha.nsl.easytrack.EtService.SubmitDirectMessageRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.DefaultResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_PARTICIPANTS:
          serviceImpl.retrieveParticipants((inha.nsl.easytrack.EtService.RetrieveParticipantsRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveParticipantsResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_PARTICIPANT_STATISTICS:
          serviceImpl.retrieveParticipantStatistics((inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveParticipantStatisticsResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE100DATA_RECORDS:
          serviceImpl.retrieve100DataRecords((inha.nsl.easytrack.EtService.Retrieve100DataRecordsRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.Retrieve100DataRecordsResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_FILTERED_DATA_RECORDS:
          serviceImpl.retrieveFilteredDataRecords((inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveFilteredDataRecordsResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_UNREAD_DIRECT_MESSAGES:
          serviceImpl.retrieveUnreadDirectMessages((inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveUnreadDirectMessagesResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_UNREAD_NOTIFICATIONS:
          serviceImpl.retrieveUnreadNotifications((inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveUnreadNotificationsResponseMessage>) responseObserver);
          break;
        case METHODID_BIND_DATA_SOURCE:
          serviceImpl.bindDataSource((inha.nsl.easytrack.EtService.BindDataSourceRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.BindDataSourceResponseMessage>) responseObserver);
          break;
        case METHODID_RETRIEVE_ALL_DATA_SOURCES:
          serviceImpl.retrieveAllDataSources((inha.nsl.easytrack.EtService.RetrieveAllDataSourcesRequestMessage) request,
              (io.grpc.stub.StreamObserver<inha.nsl.easytrack.EtService.RetrieveAllDataSourcesResponseMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ETServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getLoginWithGoogleIdMethod())
              .addMethod(getBindUserToCampaignMethod())
              .addMethod(getDashboardLoginWithEmailMethod())
              .addMethod(getRegisterCampaignMethod())
              .addMethod(getDeleteCampaignMethod())
              .addMethod(getRetrieveCampaignsMethod())
              .addMethod(getRetrieveCampaignMethod())
              .addMethod(getSubmitDataRecordMethod())
              .addMethod(getSubmitHeartbeatMethod())
              .addMethod(getSubmitDirectMessageMethod())
              .addMethod(getRetrieveParticipantsMethod())
              .addMethod(getRetrieveParticipantStatisticsMethod())
              .addMethod(getRetrieve100DataRecordsMethod())
              .addMethod(getRetrieveFilteredDataRecordsMethod())
              .addMethod(getRetrieveUnreadDirectMessagesMethod())
              .addMethod(getRetrieveUnreadNotificationsMethod())
              .addMethod(getBindDataSourceMethod())
              .addMethod(getRetrieveAllDataSourcesMethod())
              .build();
        }
      }
    }
    return result;
  }
}

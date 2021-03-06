/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cn.kenshinn.tsdb.thrift.models;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-10-26")
public class ThriftTsdbRpcService {

  public interface Iface {

    public void putTSData(TSData tsdata) throws org.apache.thrift.TException;

    public void putString(String metrics) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void putTSData(TSData tsdata, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

    public void putString(String metrics, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public void putTSData(TSData tsdata) throws org.apache.thrift.TException
    {
      send_putTSData(tsdata);
    }

    public void send_putTSData(TSData tsdata) throws org.apache.thrift.TException
    {
      putTSData_args args = new putTSData_args();
      args.setTsdata(tsdata);
      sendBaseOneway("putTSData", args);
    }

    public void putString(String metrics) throws org.apache.thrift.TException
    {
      send_putString(metrics);
    }

    public void send_putString(String metrics) throws org.apache.thrift.TException
    {
      putString_args args = new putString_args();
      args.setMetrics(metrics);
      sendBaseOneway("putString", args);
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void putTSData(TSData tsdata, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      putTSData_call method_call = new putTSData_call(tsdata, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class putTSData_call extends org.apache.thrift.async.TAsyncMethodCall {
      private TSData tsdata;
      public putTSData_call(TSData tsdata, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, true);
        this.tsdata = tsdata;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("putTSData", org.apache.thrift.protocol.TMessageType.ONEWAY, 0));
        putTSData_args args = new putTSData_args();
        args.setTsdata(tsdata);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
      }
    }

    public void putString(String metrics, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      putString_call method_call = new putString_call(metrics, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class putString_call extends org.apache.thrift.async.TAsyncMethodCall {
      private String metrics;
      public putString_call(String metrics, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, true);
        this.metrics = metrics;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("putString", org.apache.thrift.protocol.TMessageType.ONEWAY, 0));
        putString_args args = new putString_args();
        args.setMetrics(metrics);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("putTSData", new putTSData());
      processMap.put("putString", new putString());
      return processMap;
    }

    public static class putTSData<I extends Iface> extends org.apache.thrift.ProcessFunction<I, putTSData_args> {
      public putTSData() {
        super("putTSData");
      }

      public putTSData_args getEmptyArgsInstance() {
        return new putTSData_args();
      }

      protected boolean isOneway() {
        return true;
      }

      public org.apache.thrift.TBase getResult(I iface, putTSData_args args) throws org.apache.thrift.TException {
        iface.putTSData(args.tsdata);
        return null;
      }
    }

    public static class putString<I extends Iface> extends org.apache.thrift.ProcessFunction<I, putString_args> {
      public putString() {
        super("putString");
      }

      public putString_args getEmptyArgsInstance() {
        return new putString_args();
      }

      protected boolean isOneway() {
        return true;
      }

      public org.apache.thrift.TBase getResult(I iface, putString_args args) throws org.apache.thrift.TException {
        iface.putString(args.metrics);
        return null;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("putTSData", new putTSData());
      processMap.put("putString", new putString());
      return processMap;
    }

    public static class putTSData<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, putTSData_args, Void> {
      public putTSData() {
        super("putTSData");
      }

      public putTSData_args getEmptyArgsInstance() {
        return new putTSData_args();
      }

      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Void>() {
          public void onComplete(Void o) {
          }
          public void onError(Exception e) {
          }
        };
      }

      protected boolean isOneway() {
        return true;
      }

      public void start(I iface, putTSData_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
        iface.putTSData(args.tsdata,resultHandler);
      }
    }

    public static class putString<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, putString_args, Void> {
      public putString() {
        super("putString");
      }

      public putString_args getEmptyArgsInstance() {
        return new putString_args();
      }

      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Void>() {
          public void onComplete(Void o) {
          }
          public void onError(Exception e) {
          }
        };
      }

      protected boolean isOneway() {
        return true;
      }

      public void start(I iface, putString_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
        iface.putString(args.metrics,resultHandler);
      }
    }

  }

  public static class putTSData_args implements org.apache.thrift.TBase<putTSData_args, putTSData_args._Fields>, java.io.Serializable, Cloneable, Comparable<putTSData_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("putTSData_args");

    private static final org.apache.thrift.protocol.TField TSDATA_FIELD_DESC = new org.apache.thrift.protocol.TField("tsdata", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new putTSData_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new putTSData_argsTupleSchemeFactory());
    }

    public TSData tsdata; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      TSDATA((short)1, "tsdata");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // TSDATA
            return TSDATA;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.TSDATA, new org.apache.thrift.meta_data.FieldMetaData("tsdata", org.apache.thrift.TFieldRequirementType.DEFAULT,
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TSData.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(putTSData_args.class, metaDataMap);
    }

    public putTSData_args() {
    }

    public putTSData_args(
      TSData tsdata)
    {
      this();
      this.tsdata = tsdata;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public putTSData_args(putTSData_args other) {
      if (other.isSetTsdata()) {
        this.tsdata = new TSData(other.tsdata);
      }
    }

    public putTSData_args deepCopy() {
      return new putTSData_args(this);
    }

    @Override
    public void clear() {
      this.tsdata = null;
    }

    public TSData getTsdata() {
      return this.tsdata;
    }

    public putTSData_args setTsdata(TSData tsdata) {
      this.tsdata = tsdata;
      return this;
    }

    public void unsetTsdata() {
      this.tsdata = null;
    }

    /** Returns true if field tsdata is set (has been assigned a value) and false otherwise */
    public boolean isSetTsdata() {
      return this.tsdata != null;
    }

    public void setTsdataIsSet(boolean value) {
      if (!value) {
        this.tsdata = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case TSDATA:
        if (value == null) {
          unsetTsdata();
        } else {
          setTsdata((TSData)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case TSDATA:
        return getTsdata();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case TSDATA:
        return isSetTsdata();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof putTSData_args)
        return this.equals((putTSData_args)that);
      return false;
    }

    public boolean equals(putTSData_args that) {
      if (that == null)
        return false;

      boolean this_present_tsdata = true && this.isSetTsdata();
      boolean that_present_tsdata = true && that.isSetTsdata();
      if (this_present_tsdata || that_present_tsdata) {
        if (!(this_present_tsdata && that_present_tsdata))
          return false;
        if (!this.tsdata.equals(that.tsdata))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_tsdata = true && (isSetTsdata());
      list.add(present_tsdata);
      if (present_tsdata)
        list.add(tsdata);

      return list.hashCode();
    }

    @Override
    public int compareTo(putTSData_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetTsdata()).compareTo(other.isSetTsdata());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetTsdata()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tsdata, other.tsdata);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("putTSData_args(");
      boolean first = true;

      sb.append("tsdata:");
      if (this.tsdata == null) {
        sb.append("null");
      } else {
        sb.append(this.tsdata);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (tsdata != null) {
        tsdata.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class putTSData_argsStandardSchemeFactory implements SchemeFactory {
      public putTSData_argsStandardScheme getScheme() {
        return new putTSData_argsStandardScheme();
      }
    }

    private static class putTSData_argsStandardScheme extends StandardScheme<putTSData_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, putTSData_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
            break;
          }
          switch (schemeField.id) {
            case 1: // TSDATA
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.tsdata = new TSData();
                struct.tsdata.read(iprot);
                struct.setTsdataIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, putTSData_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.tsdata != null) {
          oprot.writeFieldBegin(TSDATA_FIELD_DESC);
          struct.tsdata.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class putTSData_argsTupleSchemeFactory implements SchemeFactory {
      public putTSData_argsTupleScheme getScheme() {
        return new putTSData_argsTupleScheme();
      }
    }

    private static class putTSData_argsTupleScheme extends TupleScheme<putTSData_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, putTSData_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetTsdata()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetTsdata()) {
          struct.tsdata.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, putTSData_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.tsdata = new TSData();
          struct.tsdata.read(iprot);
          struct.setTsdataIsSet(true);
        }
      }
    }

  }

  public static class putString_args implements org.apache.thrift.TBase<putString_args, putString_args._Fields>, java.io.Serializable, Cloneable, Comparable<putString_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("putString_args");

    private static final org.apache.thrift.protocol.TField METRICS_FIELD_DESC = new org.apache.thrift.protocol.TField("metrics", org.apache.thrift.protocol.TType.STRING, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new putString_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new putString_argsTupleSchemeFactory());
    }

    public String metrics; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      METRICS((short)1, "metrics");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // METRICS
            return METRICS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.METRICS, new org.apache.thrift.meta_data.FieldMetaData("metrics", org.apache.thrift.TFieldRequirementType.DEFAULT,
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(putString_args.class, metaDataMap);
    }

    public putString_args() {
    }

    public putString_args(
      String metrics)
    {
      this();
      this.metrics = metrics;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public putString_args(putString_args other) {
      if (other.isSetMetrics()) {
        this.metrics = other.metrics;
      }
    }

    public putString_args deepCopy() {
      return new putString_args(this);
    }

    @Override
    public void clear() {
      this.metrics = null;
    }

    public String getMetrics() {
      return this.metrics;
    }

    public putString_args setMetrics(String metrics) {
      this.metrics = metrics;
      return this;
    }

    public void unsetMetrics() {
      this.metrics = null;
    }

    /** Returns true if field metrics is set (has been assigned a value) and false otherwise */
    public boolean isSetMetrics() {
      return this.metrics != null;
    }

    public void setMetricsIsSet(boolean value) {
      if (!value) {
        this.metrics = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case METRICS:
        if (value == null) {
          unsetMetrics();
        } else {
          setMetrics((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case METRICS:
        return getMetrics();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case METRICS:
        return isSetMetrics();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof putString_args)
        return this.equals((putString_args)that);
      return false;
    }

    public boolean equals(putString_args that) {
      if (that == null)
        return false;

      boolean this_present_metrics = true && this.isSetMetrics();
      boolean that_present_metrics = true && that.isSetMetrics();
      if (this_present_metrics || that_present_metrics) {
        if (!(this_present_metrics && that_present_metrics))
          return false;
        if (!this.metrics.equals(that.metrics))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_metrics = true && (isSetMetrics());
      list.add(present_metrics);
      if (present_metrics)
        list.add(metrics);

      return list.hashCode();
    }

    @Override
    public int compareTo(putString_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetMetrics()).compareTo(other.isSetMetrics());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetMetrics()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.metrics, other.metrics);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("putString_args(");
      boolean first = true;

      sb.append("metrics:");
      if (this.metrics == null) {
        sb.append("null");
      } else {
        sb.append(this.metrics);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class putString_argsStandardSchemeFactory implements SchemeFactory {
      public putString_argsStandardScheme getScheme() {
        return new putString_argsStandardScheme();
      }
    }

    private static class putString_argsStandardScheme extends StandardScheme<putString_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, putString_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
            break;
          }
          switch (schemeField.id) {
            case 1: // METRICS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.metrics = iprot.readString();
                struct.setMetricsIsSet(true);
              } else {
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, putString_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.metrics != null) {
          oprot.writeFieldBegin(METRICS_FIELD_DESC);
          oprot.writeString(struct.metrics);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class putString_argsTupleSchemeFactory implements SchemeFactory {
      public putString_argsTupleScheme getScheme() {
        return new putString_argsTupleScheme();
      }
    }

    private static class putString_argsTupleScheme extends TupleScheme<putString_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, putString_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetMetrics()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetMetrics()) {
          oprot.writeString(struct.metrics);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, putString_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.metrics = iprot.readString();
          struct.setMetricsIsSet(true);
        }
      }
    }

  }

}

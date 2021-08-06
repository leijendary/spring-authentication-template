/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.leijendary.schema;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class AuthSchema extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7621710446236726307L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AuthSchema\",\"namespace\":\"com.leijendary.schema\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"userId\",\"type\":\"long\"},{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"audience\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"deviceId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"createdDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<AuthSchema> ENCODER =
      new BinaryMessageEncoder<AuthSchema>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<AuthSchema> DECODER =
      new BinaryMessageDecoder<AuthSchema>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<AuthSchema> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<AuthSchema> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<AuthSchema> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<AuthSchema>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this AuthSchema to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a AuthSchema from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a AuthSchema instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static AuthSchema fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private long id;
   private long userId;
   private java.lang.String username;
   private java.lang.String audience;
   private java.lang.String type;
   private java.lang.String deviceId;
   private java.lang.String createdDate;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AuthSchema() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param userId The new value for userId
   * @param username The new value for username
   * @param audience The new value for audience
   * @param type The new value for type
   * @param deviceId The new value for deviceId
   * @param createdDate The new value for createdDate
   */
  public AuthSchema(java.lang.Long id, java.lang.Long userId, java.lang.String username, java.lang.String audience, java.lang.String type, java.lang.String deviceId, java.lang.String createdDate) {
    this.id = id;
    this.userId = userId;
    this.username = username;
    this.audience = audience;
    this.type = type;
    this.deviceId = deviceId;
    this.createdDate = createdDate;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return userId;
    case 2: return username;
    case 3: return audience;
    case 4: return type;
    case 5: return deviceId;
    case 6: return createdDate;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Long)value$; break;
    case 1: userId = (java.lang.Long)value$; break;
    case 2: username = value$ != null ? value$.toString() : null; break;
    case 3: audience = value$ != null ? value$.toString() : null; break;
    case 4: type = value$ != null ? value$.toString() : null; break;
    case 5: deviceId = value$ != null ? value$.toString() : null; break;
    case 6: createdDate = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public long getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(long value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'userId' field.
   * @return The value of the 'userId' field.
   */
  public long getUserId() {
    return userId;
  }


  /**
   * Sets the value of the 'userId' field.
   * @param value the value to set.
   */
  public void setUserId(long value) {
    this.userId = value;
  }

  /**
   * Gets the value of the 'username' field.
   * @return The value of the 'username' field.
   */
  public java.lang.String getUsername() {
    return username;
  }


  /**
   * Sets the value of the 'username' field.
   * @param value the value to set.
   */
  public void setUsername(java.lang.String value) {
    this.username = value;
  }

  /**
   * Gets the value of the 'audience' field.
   * @return The value of the 'audience' field.
   */
  public java.lang.String getAudience() {
    return audience;
  }


  /**
   * Sets the value of the 'audience' field.
   * @param value the value to set.
   */
  public void setAudience(java.lang.String value) {
    this.audience = value;
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.String getType() {
    return type;
  }


  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.String value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'deviceId' field.
   * @return The value of the 'deviceId' field.
   */
  public java.lang.String getDeviceId() {
    return deviceId;
  }


  /**
   * Sets the value of the 'deviceId' field.
   * @param value the value to set.
   */
  public void setDeviceId(java.lang.String value) {
    this.deviceId = value;
  }

  /**
   * Gets the value of the 'createdDate' field.
   * @return The value of the 'createdDate' field.
   */
  public java.lang.String getCreatedDate() {
    return createdDate;
  }


  /**
   * Sets the value of the 'createdDate' field.
   * @param value the value to set.
   */
  public void setCreatedDate(java.lang.String value) {
    this.createdDate = value;
  }

  /**
   * Creates a new AuthSchema RecordBuilder.
   * @return A new AuthSchema RecordBuilder
   */
  public static com.leijendary.schema.AuthSchema.Builder newBuilder() {
    return new com.leijendary.schema.AuthSchema.Builder();
  }

  /**
   * Creates a new AuthSchema RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AuthSchema RecordBuilder
   */
  public static com.leijendary.schema.AuthSchema.Builder newBuilder(com.leijendary.schema.AuthSchema.Builder other) {
    if (other == null) {
      return new com.leijendary.schema.AuthSchema.Builder();
    } else {
      return new com.leijendary.schema.AuthSchema.Builder(other);
    }
  }

  /**
   * Creates a new AuthSchema RecordBuilder by copying an existing AuthSchema instance.
   * @param other The existing instance to copy.
   * @return A new AuthSchema RecordBuilder
   */
  public static com.leijendary.schema.AuthSchema.Builder newBuilder(com.leijendary.schema.AuthSchema other) {
    if (other == null) {
      return new com.leijendary.schema.AuthSchema.Builder();
    } else {
      return new com.leijendary.schema.AuthSchema.Builder(other);
    }
  }

  /**
   * RecordBuilder for AuthSchema instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AuthSchema>
    implements org.apache.avro.data.RecordBuilder<AuthSchema> {

    private long id;
    private long userId;
    private java.lang.String username;
    private java.lang.String audience;
    private java.lang.String type;
    private java.lang.String deviceId;
    private java.lang.String createdDate;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.leijendary.schema.AuthSchema.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.userId)) {
        this.userId = data().deepCopy(fields()[1].schema(), other.userId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.username)) {
        this.username = data().deepCopy(fields()[2].schema(), other.username);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.audience)) {
        this.audience = data().deepCopy(fields()[3].schema(), other.audience);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.type)) {
        this.type = data().deepCopy(fields()[4].schema(), other.type);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.deviceId)) {
        this.deviceId = data().deepCopy(fields()[5].schema(), other.deviceId);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.createdDate)) {
        this.createdDate = data().deepCopy(fields()[6].schema(), other.createdDate);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
    }

    /**
     * Creates a Builder by copying an existing AuthSchema instance
     * @param other The existing instance to copy.
     */
    private Builder(com.leijendary.schema.AuthSchema other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.userId)) {
        this.userId = data().deepCopy(fields()[1].schema(), other.userId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.username)) {
        this.username = data().deepCopy(fields()[2].schema(), other.username);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.audience)) {
        this.audience = data().deepCopy(fields()[3].schema(), other.audience);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.type)) {
        this.type = data().deepCopy(fields()[4].schema(), other.type);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.deviceId)) {
        this.deviceId = data().deepCopy(fields()[5].schema(), other.deviceId);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.createdDate)) {
        this.createdDate = data().deepCopy(fields()[6].schema(), other.createdDate);
        fieldSetFlags()[6] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public long getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setId(long value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'userId' field.
      * @return The value.
      */
    public long getUserId() {
      return userId;
    }


    /**
      * Sets the value of the 'userId' field.
      * @param value The value of 'userId'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setUserId(long value) {
      validate(fields()[1], value);
      this.userId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'userId' field has been set.
      * @return True if the 'userId' field has been set, false otherwise.
      */
    public boolean hasUserId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'userId' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearUserId() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'username' field.
      * @return The value.
      */
    public java.lang.String getUsername() {
      return username;
    }


    /**
      * Sets the value of the 'username' field.
      * @param value The value of 'username'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setUsername(java.lang.String value) {
      validate(fields()[2], value);
      this.username = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'username' field has been set.
      * @return True if the 'username' field has been set, false otherwise.
      */
    public boolean hasUsername() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'username' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearUsername() {
      username = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'audience' field.
      * @return The value.
      */
    public java.lang.String getAudience() {
      return audience;
    }


    /**
      * Sets the value of the 'audience' field.
      * @param value The value of 'audience'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setAudience(java.lang.String value) {
      validate(fields()[3], value);
      this.audience = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'audience' field has been set.
      * @return True if the 'audience' field has been set, false otherwise.
      */
    public boolean hasAudience() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'audience' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearAudience() {
      audience = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.String getType() {
      return type;
    }


    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setType(java.lang.String value) {
      validate(fields()[4], value);
      this.type = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearType() {
      type = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'deviceId' field.
      * @return The value.
      */
    public java.lang.String getDeviceId() {
      return deviceId;
    }


    /**
      * Sets the value of the 'deviceId' field.
      * @param value The value of 'deviceId'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setDeviceId(java.lang.String value) {
      validate(fields()[5], value);
      this.deviceId = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'deviceId' field has been set.
      * @return True if the 'deviceId' field has been set, false otherwise.
      */
    public boolean hasDeviceId() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'deviceId' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearDeviceId() {
      deviceId = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdDate' field.
      * @return The value.
      */
    public java.lang.String getCreatedDate() {
      return createdDate;
    }


    /**
      * Sets the value of the 'createdDate' field.
      * @param value The value of 'createdDate'.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder setCreatedDate(java.lang.String value) {
      validate(fields()[6], value);
      this.createdDate = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'createdDate' field has been set.
      * @return True if the 'createdDate' field has been set, false otherwise.
      */
    public boolean hasCreatedDate() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'createdDate' field.
      * @return This builder.
      */
    public com.leijendary.schema.AuthSchema.Builder clearCreatedDate() {
      createdDate = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public AuthSchema build() {
      try {
        AuthSchema record = new AuthSchema();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
        record.userId = fieldSetFlags()[1] ? this.userId : (java.lang.Long) defaultValue(fields()[1]);
        record.username = fieldSetFlags()[2] ? this.username : (java.lang.String) defaultValue(fields()[2]);
        record.audience = fieldSetFlags()[3] ? this.audience : (java.lang.String) defaultValue(fields()[3]);
        record.type = fieldSetFlags()[4] ? this.type : (java.lang.String) defaultValue(fields()[4]);
        record.deviceId = fieldSetFlags()[5] ? this.deviceId : (java.lang.String) defaultValue(fields()[5]);
        record.createdDate = fieldSetFlags()[6] ? this.createdDate : (java.lang.String) defaultValue(fields()[6]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<AuthSchema>
    WRITER$ = (org.apache.avro.io.DatumWriter<AuthSchema>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<AuthSchema>
    READER$ = (org.apache.avro.io.DatumReader<AuthSchema>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeLong(this.id);

    out.writeLong(this.userId);

    out.writeString(this.username);

    out.writeString(this.audience);

    out.writeString(this.type);

    out.writeString(this.deviceId);

    out.writeString(this.createdDate);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.id = in.readLong();

      this.userId = in.readLong();

      this.username = in.readString();

      this.audience = in.readString();

      this.type = in.readString();

      this.deviceId = in.readString();

      this.createdDate = in.readString();

    } else {
      for (int i = 0; i < 7; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.id = in.readLong();
          break;

        case 1:
          this.userId = in.readLong();
          break;

        case 2:
          this.username = in.readString();
          break;

        case 3:
          this.audience = in.readString();
          break;

        case 4:
          this.type = in.readString();
          break;

        case 5:
          this.deviceId = in.readString();
          break;

        case 6:
          this.createdDate = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










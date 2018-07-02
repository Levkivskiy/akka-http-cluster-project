package model.gameInform

import org.bson.codecs.{Codec, DecoderContext, EncoderContext}
import org.bson.{BsonReader, BsonWriter}
import org.joda.time.DateTime

//Парсер DateTime в bson тип

class JodaCodec extends Codec[DateTime] {

  override def decode(bsonReader: BsonReader, decoderContext: DecoderContext): DateTime = new DateTime(bsonReader.readDateTime())

  override def encode(bsonWriter: BsonWriter, t: DateTime, encoderContext: EncoderContext): Unit = bsonWriter.writeDateTime(t.getMillis)

  override def getEncoderClass: Class[DateTime] = classOf[DateTime]
}
/*
val codecRegistry = fromRegistries(
fromProviders(classOf[Record]),
CodecRegistries.fromCodecs(new JodaCodec),
DEFAULT_CODEC_REGISTRY
)*/
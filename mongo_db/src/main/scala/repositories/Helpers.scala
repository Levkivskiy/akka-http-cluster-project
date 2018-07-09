package repositories

import java.util.concurrent.TimeUnit

import org.mongodb.scala.{Document, Observable}

trait Helpers {

  implicit class DocumentObservable[C](val observable: Observable[Document])
    extends ImplicitObservable[Document] {
    override val converter: (Document) =>
      String = (doc) => doc.toJson
  }

  implicit class GenericObservable[C](val observable: Observable[C])
    extends ImplicitObservable[C] {
    override val converter: (C) =>
      String = (doc) => doc.toString
  }


  trait ImplicitObservable[C] {
    val observable: Observable[C]
    val converter: (C) => String
  }

}
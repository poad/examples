package com.github.poad.scala.springboot.example.entity

import javax.persistence.{Column, Entity, Id, Table}

import scala.beans.BeanProperty

@Entity
@Table(name="message")
case class MessageEntity(@BeanProperty @Column(name="message") var message: String) extends Serializable {
  // default constructor for JPA
  def this() {
    this(null)
  }

  @Id
  @Column(name="id")
  @BeanProperty
  var id: String = _
}

package com.github.poad.scala.springboot.example.content

import javax.xml.bind.annotation.{XmlElement, XmlRootElement}

import scala.beans.BeanProperty

@XmlRootElement
case class Message(@BeanProperty @XmlElement id: String, @BeanProperty @XmlElement message: String) {

}

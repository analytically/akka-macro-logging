/*
 * Copyright 2015 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.heikoseeberger.akkamacrologging

import akka.event.{ LoggingAdapter => AkkaLoggingAdapter, Logging }
import org.scalatest.{ FlatSpec, Matchers }

object LoggingAdapterSpec {

  class MockLoggerAdapter(logLevel: Logging.LogLevel) extends AkkaLoggingAdapter {

    var message: Option[String] = None

    override def isErrorEnabled = logLevel >= Logging.ErrorLevel

    override def isWarningEnabled = logLevel >= Logging.WarningLevel

    override def isInfoEnabled = logLevel >= Logging.InfoLevel

    override def isDebugEnabled = logLevel >= Logging.DebugLevel

    override protected def notifyError(message: String) = this.message = Some(message)

    override protected def notifyError(cause: Throwable, message: String) = this.message = Some(message)

    override protected def notifyWarning(message: String) = this.message = Some(message)

    override protected def notifyInfo(message: String) = this.message = Some(message)

    override protected def notifyDebug(message: String) = this.message = Some(message)
  }

  class Expression[A](value: A) {

    private var _isEvaluated = false

    def apply(): A = {
      _isEvaluated = true
      value
    }

    def isEvaluated: Boolean = _isEvaluated
  }
}

class LoggingAdapterSpec extends FlatSpec with Matchers {
  import LoggingAdapterSpec._

  behavior of "LoggingAdapter.error"

  it should "neither evaluate the cause, message nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val message = new Expression("message")
    loggingAdapter.error(cause(), message())
    cause.isEvaluated shouldBe false
    message.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the cause, message and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val message = new Expression("message")
    loggingAdapter.error(cause(), message())
    cause.isEvaluated shouldBe true
    message.isEvaluated shouldBe true
    underlying.message shouldBe Some("message")
  }

  it should "neither evaluate the cause, message and single argument nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.error(cause(), template(), arg1())
    cause.isEvaluated shouldBe false
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the cause, message and single argument and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.error(cause(), template(), arg1())
    cause.isEvaluated shouldBe true
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1")
  }

  it should "neither evaluate the cause, message and two arguments nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.error(cause(), template(), arg1(), arg2())
    cause.isEvaluated shouldBe false
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the cause, message and and two arguments and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.error(cause(), template(), arg1(), arg2())
    cause.isEvaluated shouldBe true
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2")
  }

  it should "neither evaluate the cause, message and three arguments nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.error(cause(), template(), arg1(), arg2(), arg3())
    cause.isEvaluated shouldBe false
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the cause, message and and three arguments and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.error(cause(), template(), arg1(), arg2(), arg3())
    cause.isEvaluated shouldBe true
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3")
  }

  it should "neither evaluate the cause, message and four arguments nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.error(cause(), template(), arg1(), arg2(), arg3(), arg4())
    cause.isEvaluated shouldBe false
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    arg4.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the cause, message and and four arguments and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val cause = new Expression(new Exception)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.error(cause(), template(), arg1(), arg2(), arg3(), arg4())
    cause.isEvaluated shouldBe true
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    arg4.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3 arg4")
  }

  it should "neither evaluate the message nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.error(message())
    message.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.error(message())
    message.isEvaluated shouldBe true
    underlying.message shouldBe Some("message")
  }

  it should "neither evaluate the message and single argument nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.error(template(), arg1())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and single argument and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.error(template(), arg1())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1")
  }

  it should "neither evaluate the message and two arguments nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.error(template(), arg1(), arg2())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and two arguments and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.error(template(), arg1(), arg2())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2")
  }

  it should "neither evaluate the message and three arguments nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.error(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and three arguments and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.error(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3")
  }

  it should "neither evaluate the message and four arguments nor call error on the underlying Akka LoggerAdapter if ErrorLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.LogLevel(0))
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.error(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    arg4.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and four arguments and call error on the underlying Akka LoggerAdapter if ErrorLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.error(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    arg4.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3 arg4")
  }

  behavior of "LoggingAdapter.warning"

  it should "neither evaluate the message nor call warning on the underlying Akka LoggerAdapter if WarningLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.warning(message())
    message.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and call warning on the underlying Akka LoggerAdapter if WarningLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.warning(message())
    message.isEvaluated shouldBe true
    underlying.message shouldBe Some("message")
  }

  it should "neither evaluate the message and single argument nor call warning on the underlying Akka LoggerAdapter if WarningLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.warning(template(), arg1())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and single argument and call warning on the underlying Akka LoggerAdapter if WarningLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.warning(template(), arg1())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1")
  }

  it should "neither evaluate the message and two arguments nor call warning on the underlying Akka LoggerAdapter if WarningLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.warning(template(), arg1(), arg2())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and two arguments and call warning on the underlying Akka LoggerAdapter if WarningLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.warning(template(), arg1(), arg2())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2")
  }

  it should "neither evaluate the message and three arguments nor call warning on the underlying Akka LoggerAdapter if WarningLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.warning(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and three arguments and call warning on the underlying Akka LoggerAdapter if WarningLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.warning(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3")
  }

  it should "neither evaluate the message and four arguments nor call warning on the underlying Akka LoggerAdapter if WarningLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.ErrorLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.warning(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    arg4.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and four arguments and call warning on the underlying Akka LoggerAdapter if WarningLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.warning(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    arg4.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3 arg4")
  }

  behavior of "LoggingAdapter.info"

  it should "neither evaluate the message nor call info on the underlying Akka LoggerAdapter if InfoLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.info(message())
    message.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and call info on the underlying Akka LoggerAdapter if InfoLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.info(message())
    message.isEvaluated shouldBe true
    underlying.message shouldBe Some("message")
  }

  it should "neither evaluate the message and single argument nor call info on the underlying Akka LoggerAdapter if InfoLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.info(template(), arg1())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and single argument and call info on the underlying Akka LoggerAdapter if InfoLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.info(template(), arg1())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1")
  }

  it should "neither evaluate the message and two arguments nor call info on the underlying Akka LoggerAdapter if InfoLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.info(template(), arg1(), arg2())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and two arguments and call info on the underlying Akka LoggerAdapter if InfoLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.info(template(), arg1(), arg2())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2")
  }

  it should "neither evaluate the message and three arguments nor call info on the underlying Akka LoggerAdapter if InfoLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.info(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and three arguments and call info on the underlying Akka LoggerAdapter if InfoLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.info(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3")
  }

  it should "neither evaluate the message and four arguments nor call info on the underlying Akka LoggerAdapter if InfoLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.WarningLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.info(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    arg4.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and four arguments and call info on the underlying Akka LoggerAdapter if InfoLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.info(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    arg4.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3 arg4")
  }

  behavior of "LoggingAdapter.debug"

  it should "neither evaluate the message nor call debug on the underlying Akka LoggerAdapter if DebugLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.debug(message())
    message.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and call debug on the underlying Akka LoggerAdapter if DebugLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.DebugLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val message = new Expression("message")
    loggingAdapter.debug(message())
    message.isEvaluated shouldBe true
    underlying.message shouldBe Some("message")
  }

  it should "neither evaluate the message and single argument nor call debug on the underlying Akka LoggerAdapter if DebugLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.debug(template(), arg1())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and single argument and call debug on the underlying Akka LoggerAdapter if DebugLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.DebugLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {}")
    val arg1 = new Expression("arg1")
    loggingAdapter.debug(template(), arg1())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1")
  }

  it should "neither evaluate the message and two arguments nor call debug on the underlying Akka LoggerAdapter if DebugLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.debug(template(), arg1(), arg2())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and two arguments and call debug on the underlying Akka LoggerAdapter if DebugLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.DebugLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    loggingAdapter.debug(template(), arg1(), arg2())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2")
  }

  it should "neither evaluate the message and three arguments nor call debug on the underlying Akka LoggerAdapter if DebugLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.debug(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and three arguments and call debug on the underlying Akka LoggerAdapter if DebugLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.DebugLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    loggingAdapter.debug(template(), arg1(), arg2(), arg3())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3")
  }

  it should "neither evaluate the message and four arguments nor call debug on the underlying Akka LoggerAdapter if DebugLevel not enabled" in {
    val underlying = new MockLoggerAdapter(Logging.InfoLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.debug(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe false
    arg1.isEvaluated shouldBe false
    arg2.isEvaluated shouldBe false
    arg3.isEvaluated shouldBe false
    arg4.isEvaluated shouldBe false
    underlying.message shouldBe None
  }

  it should "evaluate the message and and four arguments and call debug on the underlying Akka LoggerAdapter if DebugLevel enabled" in {
    val underlying = new MockLoggerAdapter(Logging.DebugLevel)
    val loggingAdapter = new LoggingAdapter(underlying)
    val template = new Expression("template {} {} {} {}")
    val arg1 = new Expression("arg1")
    val arg2 = new Expression("arg2")
    val arg3 = new Expression("arg3")
    val arg4 = new Expression("arg4")
    loggingAdapter.debug(template(), arg1(), arg2(), arg3(), arg4())
    template.isEvaluated shouldBe true
    arg1.isEvaluated shouldBe true
    arg2.isEvaluated shouldBe true
    arg3.isEvaluated shouldBe true
    arg4.isEvaluated shouldBe true
    underlying.message shouldBe Some("template arg1 arg2 arg3 arg4")
  }
}

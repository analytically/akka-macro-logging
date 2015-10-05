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

import akka.actor.Actor
import akka.event.{ Logging, LoggingAdapter => AkkaLoggingAdapter }

trait ActorLogging { this: Actor =>

  private var _log: LoggingAdapter = _

  def log: LoggingAdapter = {
    if (_log eq null) _log = new LoggingAdapter(Logging(context.system, this))
    _log
  }
}

final class LoggingAdapter private[akkamacrologging] (val underlying: AkkaLoggingAdapter) {
  import LoggingAdapterMacro._

  def error(cause: Throwable, message: String): Unit = macro errorC0

  def error(cause: Throwable, template: String, arg1: Any): Unit = macro errorC1

  def error(cause: Throwable, template: String, arg1: Any, arg2: Any): Unit = macro errorC2

  def error(cause: Throwable, template: String, arg1: Any, arg2: Any, arg3: Any): Unit = macro errorC3

  def error(cause: Throwable, template: String, arg1: Any, arg2: Any, arg3: Any, arg4: Any): Unit = macro errorC4

  def error(message: String): Unit = macro error0

  def error(template: String, arg1: Any): Unit = macro error1

  def error(template: String, arg1: Any, arg2: Any): Unit = macro error2

  def error(template: String, arg1: Any, arg2: Any, arg3: Any): Unit = macro error3

  def error(template: String, arg1: Any, arg2: Any, arg3: Any, arg4: Any): Unit = macro error4

  def warning(message: String): Unit = macro warning0

  def warning(template: String, arg1: Any): Unit = macro warning1

  def warning(template: String, arg1: Any, arg2: Any): Unit = macro warning2

  def warning(template: String, arg1: Any, arg2: Any, arg3: Any): Unit = macro warning3

  def warning(template: String, arg1: Any, arg2: Any, arg3: Any, arg4: Any): Unit = macro warning4

  def info(message: String): Unit = macro info0

  def info(template: String, arg1: Any): Unit = macro info1

  def info(template: String, arg1: Any, arg2: Any): Unit = macro info2

  def info(template: String, arg1: Any, arg2: Any, arg3: Any): Unit = macro info3

  def info(template: String, arg1: Any, arg2: Any, arg3: Any, arg4: Any): Unit = macro info4

  def debug(message: String): Unit = macro debug0

  def debug(template: String, arg1: Any): Unit = macro debug1

  def debug(template: String, arg1: Any, arg2: Any): Unit = macro debug2

  def debug(template: String, arg1: Any, arg2: Any, arg3: Any): Unit = macro debug3

  def debug(template: String, arg1: Any, arg2: Any, arg3: Any, arg4: Any): Unit = macro debug4
}

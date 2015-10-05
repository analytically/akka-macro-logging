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

import scala.reflect.macros.blackbox.Context

private object LoggingAdapterMacro {

  type C = Context { type PrefixType = LoggingAdapter }

  def errorC0(c: C)(cause: c.Expr[Throwable], message: c.Expr[String]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($cause, $message)"
  }

  def errorC1(c: C)(cause: c.Expr[Throwable], template: c.Expr[String], arg1: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($cause, $template, $arg1)"
  }

  def errorC2(c: C)(cause: c.Expr[Throwable], template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($cause, $template, $arg1, $arg2)"
  }

  def errorC3(c: C)(cause: c.Expr[Throwable], template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($cause, $template, $arg1, $arg2, $arg3)"
  }

  def errorC4(c: C)(cause: c.Expr[Throwable], template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any], arg4: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($cause, $template, $arg1, $arg2, $arg3, $arg4)"
  }

  def error0(c: C)(message: c.Expr[String]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($message)"
  }

  def error1(c: C)(template: c.Expr[String], arg1: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($template, $arg1)"
  }

  def error2(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($template, $arg1, $arg2)"
  }

  def error3(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($template, $arg1, $arg2, $arg3)"
  }

  def error4(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any], arg4: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($template, $arg1, $arg2, $arg3, $arg4)"
  }

  def warning0(c: C)(message: c.Expr[String]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarningEnabled) $underlying.warning($message)"
  }

  def warning1(c: C)(template: c.Expr[String], arg1: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarningEnabled) $underlying.warning($template, $arg1)"
  }

  def warning2(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarningEnabled) $underlying.warning($template, $arg1, $arg2)"
  }

  def warning3(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarningEnabled) $underlying.warning($template, $arg1, $arg2, $arg3)"
  }

  def warning4(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any], arg4: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarningEnabled) $underlying.warning($template, $arg1, $arg2, $arg3, $arg4)"
  }

  def info0(c: C)(message: c.Expr[String]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $underlying.info($message)"
  }

  def info1(c: C)(template: c.Expr[String], arg1: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $underlying.info($template, $arg1)"
  }

  def info2(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $underlying.info($template, $arg1, $arg2)"
  }

  def info3(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $underlying.info($template, $arg1, $arg2, $arg3)"
  }

  def info4(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any], arg4: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $underlying.info($template, $arg1, $arg2, $arg3, $arg4)"
  }

  def debug0(c: C)(message: c.Expr[String]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $underlying.debug($message)"
  }

  def debug1(c: C)(template: c.Expr[String], arg1: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $underlying.debug($template, $arg1)"
  }

  def debug2(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $underlying.debug($template, $arg1, $arg2)"
  }

  def debug3(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $underlying.debug($template, $arg1, $arg2, $arg3)"
  }

  def debug4(c: C)(template: c.Expr[String], arg1: c.Expr[Any], arg2: c.Expr[Any], arg3: c.Expr[Any], arg4: c.Expr[Any]) = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $underlying.debug($template, $arg1, $arg2, $arg3, $arg4)"
  }
}

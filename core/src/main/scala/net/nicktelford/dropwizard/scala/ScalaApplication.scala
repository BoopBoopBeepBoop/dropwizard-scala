package net.nicktelford.dropwizard.scala

import io.dropwizard.setup.Bootstrap
import io.dropwizard.{Application, Configuration}

/** Base class for Dropwizard Services built in Scala.
  *
  * Building a Dropwizard Application in Scala has never been easier:
  * {{{
  * import io.dropwizard.setup.{Bootstrap, Environment}
  * import net.nicktelford.dropwizard.scala.ScalaApplication
  *
  * object MyApplication extends ScalaApplication[MyConfiguration] {
  *   def init(bootstrap: MyConfiguration) {
  *     // (optional) initialization code goes here
  *   }
  *
  *   def run(configuration: MyConfiguration, environment: Environment) {
  *     // application code goes here
  *   }
  * }
  * }}}
  *
  * Applications derived from this trait will automatically have the
  * [[net.nicktelford.dropwizard.scala.ScalaBundle]] added to them during initialization.
  **/
trait ScalaApplication[A <: Configuration] extends Application[A] {

  // when the application is a singleton object, we need to strip the trailing dollar from its name
  override def getName: String = getClass.getSimpleName.stripSuffix("$")

  /** Entry point for this Dropwizard [[io.dropwizard.Application]].
    *
    * @param args the command-line arguments the program was invoked with.
    */
  final def main(args: Array[String]) {
    run(args)
  }

  /** Service initialization.
    *
    * Ensures that [[net.nicktelford.dropwizard.scala.ScalaBundle]] is always included in Scala
    * services.
    *
    * To customize initialization behaviour, override `ScalaService#init(Bootstrap)`.
    *
    * @param bootstrap Service Bootstrap environment.
    */
  override final def initialize(bootstrap: Bootstrap[A]) {
    bootstrap.addBundle(new ScalaBundle)
    init(bootstrap)
  }

  /** Service initialization.
    *
    * @param bootstrap Service Bootstrap environment.
    */
  def init(bootstrap: Bootstrap[A]) {
    // do nothing extra by default, override to add additional initialization behavior
  }
}

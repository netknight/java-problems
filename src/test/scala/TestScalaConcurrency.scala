import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@RunWith(classOf[JUnit4])
object TestScalaConcurrency {

  def flatten1(future: Future[Option[Future[Option[AnyRef]]]]): Future[Option[String]] = {
    future.flatMap(
      _.map(
        _.map(
          _.map(_.toString)
        )
      ).getOrElse(Future.successful(None))
    )
  }

  type FOFO[T] = Future[Option[Future[Option[T]]]]

  def flatten2(future: FOFO[AnyRef]): Future[Option[String]] = {
    future.flatMap {
      case Some(x) => x map(_ map(_.toString))
      case None => Future.successful(None)
    }
  }

}

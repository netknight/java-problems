import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@RunWith(classOf[JUnit4])
object Example6 {

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

  def doAsyncAction(request: String): Future[String] = {
    Future.successful("response")
  }

  def batchOperations(future: Future[List[String]]): Future[List[String]] = {
    future.map(results =>
      Future.sequence(
        results.sliding(10).toList.map(batch =>
          Future.sequence(
            batch.map(item =>
              doAsyncAction(item)
            )
          )
        )
      ).map(_.flatten)
    ).flatten
  }
}

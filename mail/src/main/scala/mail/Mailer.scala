package mail

import com.github.jurajburian.mailer._
import javax.mail.Session
import javax.mail.internet.InternetAddress

import scala.util.Try

class Mailer(sender: String, senderPassword: String) {

  val session: Session = (SmtpAddress("smtp.gmail.com", 587)
    :: Property("mail.smtp.starttls.enable", "true")
    :: SessionFactory())
    .session(Some(sender -> senderPassword))

  def createContext(textMsg: String, htmlMsg: String) =
    Content().text(textMsg).html(htmlMsg)

  def createMsg(context: Content, subject: String, receiver: String) = Message(
    from = new InternetAddress("olevkivskiy@gmail.com"),
    subject = subject,
    content = context,
    to = Seq(new InternetAddress(receiver)))

  val mailer = Mailer(session)

  def sendMessege(msg: Message) = Try {
    mailer.send(msg)
  }

  def closeMailer = mailer.close()
}

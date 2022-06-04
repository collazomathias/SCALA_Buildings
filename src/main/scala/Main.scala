import inventory.Inventory
import functions._
import build._
import citadel.Citadel
import buildorder.BuildOrder
import java.time.LocalDate

object Main extends App {
    val inventory = Inventory(500, 500, 500, 500, 500)
    val day = LocalDate.now().getDayOfMonth()
    val month = LocalDate.now().getMonthValue()
    val year = LocalDate.now().getYear()
    val citadel = Citadel(List(), List(), List(), inventory, day, month, year)
    val citadel2 = Functions.createRequest(citadel, Edificio, 5, 5)
    val citadel3 = Functions.createRequest(citadel2, Casa, 6, 6)
    val citadel4 = Functions.createRequest(citadel3, Edificio, 7, 7)
    val citadel5 = Functions.createRequest(citadel4, Edificio, 8, 8)
    //val citadel4 = Functions.updateOrders(citadel3)
    //val citadel5 = Functions.updateOrders(citadel4)
    println(citadel5)
}
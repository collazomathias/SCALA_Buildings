import inventory.Inventory
import functions._
import build._
import citadel.Citadel
import buildorder.BuildOrder

object Main extends App {
    val list : List[(Build, (Double, Double))] = List((Casa, (0,0)), (Edificio, (1,1)), (Lago, (2,2)))
    val list2 : List[BuildOrder] = List(BuildOrder(Casa, 3, 3, 12, 6, 2022, 15, 6, 2022, "Pendiente"), BuildOrder(Edificio, 4, 4, 16, 6, 2022, 22, 6, 2022, "Pendiente"))
    val citadel = Citadel(list, list2, 12, 6, 2022)
    val inventory = Inventory(30, 30, 30, 30, 30)
    println(Functions.createRequest(citadel, inventory, Edificio, 10, 10))
}
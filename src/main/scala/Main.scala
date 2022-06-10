import inventory.Inventory
import functions._
import build._
import citadel.Citadel
import buildorder.BuildOrder
import java.time.LocalDate
import request.Request
import scala.util.control.Breaks

object Main {

    def main(args: Array[String]): Unit = {
        val finishedBuilds : List[(Build, (Integer, Integer))] = List()
        val ordersInProgress : List[BuildOrder] = List()
        val requests : List[Request] = List()
        val inventory = Inventory(1000, 1000, 1000, 1000, 1000)
        val day = LocalDate.now().getDayOfMonth()
        val month = LocalDate.now().getMonthValue()
        val year = LocalDate.now().getYear()
        val citadel = Citadel(finishedBuilds, ordersInProgress, requests, inventory, day, month, year)
        menu(citadel)
    }

    def menu(citadel : Citadel) {
        val loop = new Breaks;
        println("|------------------------------|")
        println("|    Welcome to the Citadel    |")
        println("|------------------------------|")
        println("1 => Create request")
        println("2 => Add materials to inventory")
        println("3 => Update build order")
        println("4 => Show information")
        println("0 => Exit")
        println("Please enter an option: ")
        loop.breakable {
            Iterator.continually(io.StdIn.readLine.toString)
            .takeWhile(_ != "0")
            .foreach {
                case "1" => {
                    menuCreateRequest(citadel)
                    loop.break();
                }
                case "2" => {
                    menuAddInventory(citadel)
                    loop.break();
                }
                case "3" => {
                    menu(Functions.updateOrders(citadel))
                    loop.break();
                }
                case "4" => {
                    menu(Utils.showInformation(citadel))
                    loop.break();
                }
                case _ => println("Invalid option")
            }
        }
    }

    def menuCreateRequest(citadel : Citadel) {
        val loop = new Breaks;
        println("|----------------------|")
        println("| Create a new request |")
        println("|----------------------|")
        println("1 => Casa")
        println("2 => Edificio")
        println("3 => Gimnasio")
        println("4 => Lago")
        println("5 => Cancha de fútbol")
        println("0 => Cancel")
        loop.breakable {
            Iterator.continually(io.StdIn.readLine.toString)
            .foreach {
                case "1" => {
                    println("|----------------------|")
                    println("| Create a new request |")
                    println("|----------------------|")
                    println("Type: Casa")
                    println("Please enter X coordinate: ")
                    val X = io.StdIn.readInt()
                    println("Please enter Y coordinate: ")
                    val Y = io.StdIn.readInt()
                    menu(Functions.createRequest(citadel, Casa, X, Y))
                    loop.break();
                }
                case "2" => {
                    println("|----------------------|")
                    println("| Create a new request |")
                    println("|----------------------|")
                    println("Type: Edificio")
                    println("Please enter X coordinate: ")
                    val X = io.StdIn.readInt()
                    println("Please enter Y coordinate: ")
                    val Y = io.StdIn.readInt()
                    menu(Functions.createRequest(citadel, Edificio, X, Y))
                    loop.break();
                }
                case "3" => {
                    println("|----------------------|")
                    println("| Create a new request |")
                    println("|----------------------|")
                    println("Type: Casa")
                    println("Please enter X coordinate: ")
                    val X = io.StdIn.readInt()
                    println("Please enter Y coordinate: ")
                    val Y = io.StdIn.readInt()
                    menu(Functions.createRequest(citadel, Gimnasio, X, Y))
                    loop.break();
                }
                case "4" => {
                    println("|----------------------|")
                    println("| Create a new request |")
                    println("|----------------------|")
                    println("Type: Casa")
                    println("Please enter X coordinate: ")
                    val X = io.StdIn.readInt()
                    println("Please enter Y coordinate: ")
                    val Y = io.StdIn.readInt()
                    menu(Functions.createRequest(citadel, Lago, X, Y))
                    loop.break();
                }
                case "5" => {
                    println("|----------------------|")
                    println("| Create a new request |")
                    println("|----------------------|")
                    println("Type: Casa")
                    println("Please enter X coordinate: ")
                    val X = io.StdIn.readInt()
                    println("Please enter Y coordinate: ")
                    val Y = io.StdIn.readInt()
                    menu(Functions.createRequest(citadel, CanchaDeFutbol, X, Y))
                    loop.break();
                }
                case "0" => {
                    menu(citadel)
                    loop.break();
                }
                case _ => {
                    println("Invalid option")
                }
            }
        }
    }

    def menuAddInventory(citadel : Citadel) {
        val loop = new Breaks;
        println("|----------------------------|")
        println("| Add materials to inventory |")
        println("|----------------------------|")
        println("1 => Cemento")
        println("2 => Grava")
        println("3 => Arena")
        println("4 => Madera")
        println("5 => Adobe")
        println("0 => Cancel")
        loop.breakable {
            Iterator.continually(io.StdIn.readLine.toString)
            .foreach {
                case "1" => {
                    println("|----------------------------|")
                    println("| Add materials to inventory |")
                    println("|----------------------------|")
                    println("Material: Cemento")
                    println("Please enter a quantity: ")
                    val quant = io.StdIn.readInt()
                    menu(Functions.addInventory(citadel, quant, "Cemento"))
                    loop.break();
                }
                case "2" => {
                    println("|----------------------------|")
                    println("| Add materials to inventory |")
                    println("|----------------------------|")
                    println("Material: Grava")
                    println("Please enter a quantity: ")
                    val quant = io.StdIn.readInt()
                    menu(Functions.addInventory(citadel, quant, "Grava"))
                    loop.break();
                }
                case "3" => {
                    println("|----------------------------|")
                    println("| Add materials to inventory |")
                    println("|----------------------------|")
                    println("Material: Arena")
                    println("Please enter a quantity: ")
                    val quant = io.StdIn.readInt()
                    menu(Functions.addInventory(citadel, quant, "Arena"))
                    loop.break();
                }
                case "4" => {
                    println("|----------------------------|")
                    println("| Add materials to inventory |")
                    println("|----------------------------|")
                    println("Material: Madera")
                    println("Please enter a quantity: ")
                    val quant = io.StdIn.readInt()
                    menu(Functions.addInventory(citadel, quant, "Madera"))
                    loop.break();
                }
                case "5" => {
                    println("|----------------------------|")
                    println("| Add materials to inventory |")
                    println("|----------------------------|")
                    println("Material: Adobe")
                    println("Please enter a quantity: ")
                    val quant = io.StdIn.readInt()
                    menu(Functions.addInventory(citadel, quant, "Adobe"))
                    loop.break();
                }
                case "0" => {
                    menu(citadel)
                    loop.break();
                }
                case _ => {
                    println("Invalid option")
                }
            }
        }
    }

    /*val finishedBuilds : List[(Build, (Integer, Integer))] = List()
    val ordersInProgress : List[BuildOrder] = List()
    val requests : List[Request] = List()
    val inventory = Inventory(1000, 1000, 1000, 1000, 1000)
    val day = LocalDate.now().getDayOfMonth()
    val month = LocalDate.now().getMonthValue()
    val year = LocalDate.now().getYear()
    val citadel = Citadel(finishedBuilds, ordersInProgress, requests, inventory, day, month, year)
    val citadel2 = Functions.createRequest(citadel, Edificio, 5, 5)
    val citadel3 = Functions.createRequest(citadel2, Casa, 6, 6)
    val citadel4 = Functions.createRequest(citadel3, Lago, 7, 7)
    val citadel5 = Functions.createRequest(citadel4, Gimnasio, 8, 8)
    val citadel6 = Functions.updateOrders(citadel5)
    val citadel7 = Functions.updateOrders(citadel6)
    val citadel8 = Functions.updateOrders(citadel7)
    Utils.showInformation(citadel8)*/
}
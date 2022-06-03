package build

case class Build (
    necessaryCemento : Double,
    necessaryGrava : Double,
    necessaryArena : Double,
    necessaryMadera : Double,
    necessaryAdobe : Double,
    buildingTime : Integer
)

object Casa extends Build (
    necessaryCemento = 100,
    necessaryGrava = 50,
    necessaryArena = 90,
    necessaryMadera = 20,
    necessaryAdobe = 100,
    buildingTime = 3
)

object Lago extends Build (
    necessaryCemento = 50,
    necessaryGrava = 60,
    necessaryArena = 80,
    necessaryMadera = 10,
    necessaryAdobe = 20,
    buildingTime = 2
)

object CanchaDeFutbol extends Build (
    necessaryCemento = 20,
    necessaryGrava = 20,
    necessaryArena = 20,
    necessaryMadera = 20,
    necessaryAdobe = 20,
    buildingTime = 1
)

object Edificio extends Build (
    necessaryCemento = 200,
    necessaryGrava = 100,
    necessaryArena = 180,
    necessaryMadera = 40,
    necessaryAdobe = 200,
    buildingTime = 6
)

object Gimnasio extends Build (
    necessaryCemento = 50,
    necessaryGrava = 25,
    necessaryArena = 45,
    necessaryMadera = 10,
    necessaryAdobe = 50,
    buildingTime = 2
)

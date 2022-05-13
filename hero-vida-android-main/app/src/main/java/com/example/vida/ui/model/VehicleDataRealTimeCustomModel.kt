package com.example.vida.ui.model

data class VehicleDataRealTimeCustomModel(
    val sohBms1: Double,
    val socUserBms1: Double,
    val soeBms1: Double,
    val batteryPackTemperatureBms1: Double,
    val sohBms2: Double,
    val socUserBms2: Double,
    val soeBms2: Double,
    val batteryPackTemperatureBms2: Double,
    val idBms1: String,
    val idBms2: String,
    val ignitionRelayStatus: Boolean,
    val remainingRange: Int,
) {
    override fun toString(): String {
        return "Soh Bms 1= $sohBms1 %\n" +
                "Soc User Bms 1= $socUserBms1 %\n" +
                "Soe Bms 1= $soeBms1% \n" +
                "Battery Pack Temperature Bms 1= $batteryPackTemperatureBms1 °C\n" +
                "\n" +
                "Soh Bms 2= $sohBms2 %\n" +
                "Soc User Bms 2= $socUserBms2 %\n" +
                "Soe Bms 2= $soeBms2 %\n" +
                "Battery Pack Temperature Bms 2= $batteryPackTemperatureBms2 °C\n" +
                "\n" +
                "Id Bms 1= $idBms1\n" +
                "Id Bms 2= $idBms2\n" +
                "Ignition Relay Status= $ignitionRelayStatus\n" +
                "Remaining Range= $remainingRange km"
    }
}
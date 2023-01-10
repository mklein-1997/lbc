import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import InventoryClient from "../api/inventoryClient";

class InventoryPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onAllCars', 'renderAllCars'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Event handlers
     */
    async mount() {
        this.client = new InventoryClient();

        this.dataStore.addChangeListener(this.renderAllCars);
        this.onAllCars();
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderAllCars() {
        let resultArea = document.getElementById("result-area");

        const allCars = this.dataStore.get("allCars");

        let html = "";
                for (let car of allCars) {
                    html += "<div class='content'>";
                    html += "<img src='https://gowhsehub.io/wp-content/uploads/2021/06/warehouse-and-forklift-animation.gif'>";
                    html += `<h3>${car.make} ${car.model}<h3>
                             <ul>
                                <li class="font">Make: ${car.make}</li>
                                <li class="font">Model: ${car.model}</li>
                                <li class="font">Year: ${car.year}</li>
                                <li class="font">Tracking ID: ${car.id}</li>
                            </ul>`;
                    html += "</div>";
                }

        if (!allCars) {
            resultArea.innerHTML = "No cars";
        } else {
            resultArea.innerHTML = html;
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onAllCars() {
        let result = await this.client.getAllCarsStatus(this.errorHandler);
        this.dataStore.set("allCars", result);
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const inventoryPage = new InventoryPage();
    inventoryPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
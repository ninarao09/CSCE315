//import { json } from "express";

var foodAddButtons = document.querySelectorAll('.add');
//console.log(foodAddButtons);
// console.log(foodAddButtons[0]);
// Loop through each button 
// JSON request being made
// We need to save the data received from the backend to here
var dataStored = {
    dataReceived: false
};

// Creating Layout of Macros windows
var i;
for(i = 0; i < foodAddButtons.length; i++){
    var button = foodAddButtons[i]; 
    //console.log("l",button.id);
    button.onclick = function(event) {
        var buttonClicked = event.target; 
        //console.log(event.target.id);
        //console.log("l",button.id);
        var parent = buttonClicked.parentNode;
        
        //console.log(parent);
        // var childList = parent.childNodes;

        // We need meal number and food number so that we can add calories in the back end
        var foodName, servings,mealNum, foodNum;
       // mealNum = grandma.id.charAt(grandma.id.length-1);
       // Usully id is m#f# 
        mealNum = parent.id.charAt(1);
        foodNum = buttonClicked.id.charAt(buttonClicked.id.length-1);
        //console.log("meal num",mealNum);
        //console.log("Last: ", foodNum);
        //console.log(button.id);
        // Grabing the values needed to make the api call 
        foodName = parent.querySelector(".name").value;
        servings = parent.querySelector(".serving").value;

        // This is to work to be able to remove a value
        if(buttonClicked.value == "Add"){    
            buttonClicked.value = "Remove";
        }else{
            parent.querySelector(".name").value = "";
            parent.querySelector(".serving").value = "";
            servings = 0; // This is how the backend knows to Remove food 
            buttonClicked.value = "Add";
        }
            //console.log(foodName);
            //console.log(servings);
            // We need to save our values and send that to the backend where it will
            // Make an api request
            var dataSent = {
                mNum: mealNum,
                fNum: foodNum,
                fName: foodName,
                fServing:servings
            }
            // Meal Values found will be stored also since we dont have a data storage system
            // Need to be joined since the backedn will need that data to calculate total
            // Basiaclly, we are sending back and forwared the macro values 
            var joined = Object.assign({},dataSent,dataStored);
            
            var request = {
            method:'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body:JSON.stringify(joined)
            };
            //fetch('http://localhost:5000/calorieTracker', request)
            //fetch('http://localhost:5000/calorieTracker', request)
            fetch('https://diet-pal.herokuapp.com/calorieTracker',request)
            .then(results => results.json())
            .then(convertedMeal =>{
                dataStored.dataReceived = convertedMeal;
                //console.log("Stored Values");
                console.log(dataStored);
                // We need to update the Respecitive Food Label in the Macro Window and Total label
                // 1 Select the Label
                var foodLabel = document.querySelector("#labelm" + mealNum +"f"+ foodNum);
                var valuesReceived = dataStored.dataReceived["meal"+mealNum]["food"+foodNum].values;
                //console.log(valuesReceived);
                // 2 Update that Label
                //var string = "Food " + mealNum + " Servings:" + Cals:0 Fats:0 Carbs:0"
                foodLabel.innerHTML = "Food "+foodNum + " Servings:" +valuesReceived[0]
                + " Cals: "+valuesReceived[1]+" Proteins: " +valuesReceived[2]+" Fats:"+valuesReceived[3]
                + " Carbs:" + valuesReceived[4];
                
                // Updating total Label
                var totalLabel = document.querySelector("#labelTotal");
                totalValues = dataStored.dataReceived.total;
                totalLabel.innerHTML = "Totals Cals: "+totalValues[0]+" Proteins: " +totalValues[1]+" Fats:"+totalValues[2]
                + " Carbs:" + totalValues[3];
                

            });

        console.log("Clicked");
    }
}

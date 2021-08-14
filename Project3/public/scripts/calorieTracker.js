// !!! Routing the /calorieTracker endpoints to here
"use strict";
import Express from "express";
var router = Express.Router();

// API stuff done here----------------------------------------------
// Need to install npm isntall node-fetch
import fetch from "node-fetch";

const params = {
    api_key :'opGwVIKMlU9rfbFkVAT2U2gAl1DFyIFIBO8IUl3S',
    query:'cheddar cheese',
    dataType: ["Survey (FNDDS)"],
    pagesize:5,
}
//const api_url = `https://api.nal.usda.gov/fdc/v1/foods/search?api_key=${encodeURIComponent(params.api_key)}&query=${encodeURIComponent(params.query)}&dataType=${encodeURIComponent(params.dataType)}&pageSize=${encodeURIComponent(params.pagesize)}`;

//`https://api.nal.usda.gov/fdc/v1/foods/search?api_key=${encodeURIComponent(params.api_key)}&query=${encodeURIComponent(params.query)}&dataType=$`
function getData() {
    const api_url = `https://api.nal.usda.gov/fdc/v1/foods/search?api_key=${encodeURIComponent(params.api_key)}&query=${encodeURIComponent(params.query)}&dataType=${encodeURIComponent(params.dataType)}&pageSize=${encodeURIComponent(params.pagesize)}`;

    return fetch(api_url).then(response => response.json())
}

// API STUFF DONE ABOVE ----------------------------------------------
//var response;

//We will Store all the data for front page here 
var dataSaved = {
    meal1:{
        food1:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food2:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food3:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food4:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        }
    },
    meal2:{
        food1:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food2:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food3:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food4:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        }
    },
    meal3:{
        food1:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food2:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food3:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food4:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        }
    },
    meal4:{
        food1:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food2:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food3:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        },
        food4:{
            values:[0,0,0,0,0]// Servings, Calories, Proteins, Fats, Carbs
        }
    },
    total:[0,0,0,0]// Calories, Proteins, Fats, Carbs
    
};

// !!! This is where I send Api data back shown below, to view the result use /YourEndpointName, NOT html file

router.get("/", (req,res) =>{
    console.log("First Calorie GET Request Made ");
    //var sum = parseInt(req.body.id) + 10;
    //var sum = req.body.id;

    // function was created in API section, the function may be unique to your API
    getData().then(data => {
        // This is what is returned into into the front end, using res
        res.json(
            data.foods[0].foodNutrients
            );
        //console.log(data.foods[0].foodNutrients)
    });

    //var sum = getData().then();
    //getData().then(data => sum = data );
    //console.log(sum);
    //res.send(toString(sum));
   /* res.json(
        sum
    );*/
})

router.get("/:id", (req,res) => {
   
    var sum = +req.params.id + 10;
    data.sum = sum;
    res.json(
        data
    );
    //res.sendFile(__dirname+'/public/pages/calorieTracker.html/');
  
})

router.post("/", (req, res) => {
    console.log("Calorie Tracker Request Made ");
    //console.log(req.body.id);
    //console.log(req.body);
    //var sum = parseInt(req.body.id1) + 5;//parseInt(req.body.id2);
    //data.sum = sum;
        // 0 means that we want to remove a value
        if (req.body.fServing == 0){
            
            // Need to remove selected values
            var mealNumber = "meal" + req.body.mNum;
            var foodNumber = "food" + req.body.fNum;
            dataSaved[mealNumber][foodNumber].values =[0,0,0,0,0];
            
            //  Calculated new Macros without the value
            dataSaved.total =[0,0,0,0]; // Calories, Proteins, Fats, Carbs
            for(var i = 1; i < 5; i++){
                for(var j = 1; j < 5; j++){
                    var tMealNumber = "meal" + i.toString();
                    var tFoodNumber = "food" + j.toString();
                    var servings   = parseInt(req.body.fServing);
                    var tempList = dataSaved[tMealNumber][tFoodNumber].values

                    // finding new totals
                    for (var k = 1; k < tempList.length;k++){
                        // we will add whats in total += what the values there is in datasaved*servings
                        dataSaved.total[k-1] += tempList[k]*tempList[0];
                    }
                    //console.log(dataSaved.total);
                    
                }
            }
            // Return our new data
            res.json(
                dataSaved
            );
        }else{// removing value above ^

         /*  TODO Adding values to datasaved
            1. Make an API Call
            1.a generate url
            1.b fetch API
            1.c Filter Results
            2. Calucate the new Calories Based on what was received
            3. Return

        */
        // 1.a General URL
        params.query = req.body.fName;
        console.log("Fname: ", params.query);
        //console.log(params);
        // 1.b Fetch API, we will only use the data for the First result
        var foodResults;
        // Below is an asynchronous event
        getData().then(data => {
            // Data is saved throughout the server being live we need to clear it before we make changes to it
            dataSaved.total =[0,0,0,0]; // Calories, Proteins, Fats, Carbs
            for(var i = 1; i < 5; i++){
                for(var j = 1; j < 5; j++){
                    var tMealNumber = "meal" + i.toString();
                    var tFoodNumber = "food" + j.toString();
                    var servings   = parseInt(req.body.fServing);
                     dataSaved[tMealNumber][tFoodNumber].values =[0,0,0,0,0];

                }
            }

            // If this is the first time we are sending data, else the empty array will be used
            if(req.body.dataReceived != false){
                dataSaved = req.body.dataReceived;
                // console.log("Data Received is not empty")
            }
           
            
            //console.log(dataSaved);
            // This is what is returned into into the front end, using res 
            // Api results, 
            foodResults = data.foods[0].foodNutrients
            console.log("Results Achieved");
            //console.log(data.foods[0]);
            // 1.c Filter Results
            // We will also fill out the dataSaved var
            var mealNumber = "meal" + req.body.mNum;
            var foodNumber = "food" + req.body.fNum;
            var servings   = parseInt(req.body.fServing);
            //console.log(mealNumber, " ", foodNumber, " ", servings);
            //console.log(dataSaved[mealNumber]);
            dataSaved[mealNumber][foodNumber].values[0] = servings;


            //console.log(foodResults);
            // Servings, Calories, Proteins, Fats, Carbs 
            for(var i = 0; i < foodResults.length; i++ ){
                if(foodResults[i].nutrientName == "Energy"){
                    //console.log("Calories: ", foodResults[i].value);
                    dataSaved[mealNumber][foodNumber].values[1] = foodResults[i].value;
                }
                if(foodResults[i].nutrientName == "Protein"){
                    
                    //console.log("Protein: ", foodResults[i].value);
                    dataSaved[mealNumber][foodNumber].values[2] = foodResults[i].value;
                }
                if(foodResults[i].nutrientName == "Total lipid (fat)"){
                    //console.log ("Fats: ", foodResults[i].value);
                    dataSaved[mealNumber][foodNumber].values[3] = foodResults[i].value;
                }
                if(foodResults[i].nutrientName == "Carbohydrate, by difference"){
                    //console.log("Carbs: ", foodResults[i].value);
                    dataSaved[mealNumber][foodNumber].values[4] = foodResults[i].value;
                }
                
            }

            // 2. Calculated new Macros
            dataSaved.total =[0,0,0,0]; // Calories, Proteins, Fats, Carbs
            for(var i = 1; i < 5; i++){
                for(var j = 1; j < 5; j++){
                    var tMealNumber = "meal" + i.toString();
                    var tFoodNumber = "food" + j.toString();
                    var servings   = parseInt(req.body.fServing);
                    var tempList = dataSaved[tMealNumber][tFoodNumber].values

                    for (var k = 1; k < tempList.length;k++){
                        // we will add whats in total += what the values there is in datasaved*servings
                        dataSaved.total[k-1] += tempList[k]*tempList[0];
                    }
                    //console.log(dataSaved.total);
                    
                }
            }
            console.log(dataSaved.total);

            res.json(
                dataSaved
            );

            //console.log(data.foods[0].foodNutrients)
        });
        //console.log(foodResults);
    } //else where we are adding a value

    
})


// !!! This line is needed 
export default router;
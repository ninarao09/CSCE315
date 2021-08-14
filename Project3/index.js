// We are using the es6 standard therefore we have to use import statements
// Rather than ex. app = require("express")
import Express from "express";
import path from "path";

// !!! 1. This is needed to redirect endpoints to seperate file 
import  calorieTracker from "./public/scripts/calorieTracker.js";

//import calorieTracker from "./routes/calorieTracker.js";
var __dirname = path.resolve();

const app = Express();
const port = process.env.PORT || 3000;

// To receive json object
app.use(Express.json());
app.use(Express.urlencoded({extended: true}));

// 
// !!! 2.  Use the calorieTracker.js file to handle the calorieTracker endpoints
app.use("/calorieTracker", calorieTracker);
//app.use("NAME OF FILE IN THE SCRIPTS DIRECTORY WITHOUT .JS, NAME USER IN IMPORT STATEMENT ABOVE")



// We need to link the directories that are used so that our html can link with 
// the css and src files
app.use(Express.static(__dirname + "/public"));
app.use(Express.static(__dirname + "/public/pages"));
//app.use(Express.static(__dirname + "/src"));


//Routing Landing Page
app.get("/", (req,res) => {

   res.sendFile(__dirname+'/public/pages/landingPage.html');
  
})

// This is required for heroku to know where to host website
app.listen(process.env.PORT || 3000, 
	() => console.log("Server is running..." + port));


var currentR = 1;
let isClick = false;

function onFormSubmit() {
  isClick = false;
  let xField = document.getElementById("data-form:x");
  let yField = document.getElementById("data-form:y");
  let rField = document.getElementById("data-form:r");
  let form = document.querySelector("#data-form");
  currentR = rField.value;

  let errorMessageBody = "<b>There are the following errors in the form:</b><br>";

  let valueX = xField.options[xField.selectedIndex].value;
  let valueY = yField.value;
  let valid = true;
  if (isNaN(+(valueY))) {
    errorMessageBody += "Y values should be a number<br>";
    valid = false;
  } else if (valueY.length > 12) {
    errorMessageBody += "The length of the Y should not exceed 12 symbols<br>";
    valid = false;
  } else if (parseFloat(valueY) < -3 || parseFloat(valueY) > 3) {
    errorMessageBody += "Y value should be in interval [-5; 5]<br>";
    valid = false;
  }

  if (!valid) {
    document.getElementById("errors").innerHTML = errorMessageBody;
    return;
  }

  console.log("Checking point: " + valueX + " " + valueY);

  checkPoint(valueX, valueY, currentR);

}

function onRadiusInput() {
  let rField = document.getElementById("data-form:r");
  currentR = rField.options[rField.selectedIndex].value;
  document.getElementById("graph-controls:hidden-r").value = currentR;
  drawCanvas(currentR);
}

function onCanvasClick(event) {
  isClick = true;
  var canvas = document.querySelector("#canvas");
  var rect = canvas.getBoundingClientRect();

  var left = rect.left;
  var top = rect.top;

  var x = event.clientX - left;
  var y = event.clientY - top;
  console.log("Raw data: " + x + " " + y);

  checkPoint(x, y, currentR);
}
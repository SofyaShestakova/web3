window.onload = function () {
  var now = new Date();
  let clock = document.getElementById("clock");
  clock.innerHTML = now.toLocaleTimeString();
  window.setInterval(function () {
    now = new Date();
    clock.innerHTML = now.toLocaleTimeString();
  }, 13000);
};
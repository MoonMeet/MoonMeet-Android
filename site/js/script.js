
$(document).mouseup(function(e) 
{
  if (!$("#myDropdown").is(e.target) && $("#myDropdown").has(e.target).length === 0) 
    {
      $("#myDropdown").slideUp();
    }

    if (!$("#myProfile").is(e.target) && $("#myProfile").has(e.target).length === 0) 
    {
      $("#myProfile").slideUp();
    }
});

$(window).load(function(){
  $("body").css("overflow","auto");
  $("section.loading-overlay").fadeOut("500");

});

$(document).ready(function(){
  function changeProfileImage() {
    window.open("add-profile-image");
  }
  $('[data-toggle="tooltip"]').tooltip();   

$("section.loading-overlay").css("overflow", "hidden");
  $("section.loading-overlay").css("background-color", "#193566");
  $("#state").css("overflow", "hidden");
  $("#state").css("padding", "100%");
  $(".refresh").click(function(){
    $(window).scrollTop(0);
    location.reload(true);
  });
  $(".up").click(function(){
    $(window).scrollTop(0);
});
document.getElementById("mySidenav").style.width = "0px";
function openNav() {
  document.getElementById("mySidenav").style.width = "360px"; 
}
    function closeNav() {
      document.getElementById("mySidenav").style.width = "0";
    }  
if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}
function changeProfileImage() {
  window.open("add-profile-image");
}
function play_sound() {
  var audio = new Audio('sounds/pop-sound-effect.mp3');
  audio.play();
}
})
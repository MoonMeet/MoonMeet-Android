
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
/*
$(document).on('click', '#likebtn', function(){
  var post_id = $("#post").attr('post_id');
  var action = 'like';
  $.ajax({
      url:"includes/like.php",
      method:"POST",
      data:{post_id:post_id, action:action},
      success:function(data)
      {
          alert(data);
          fetch_post();
      }
  })
  function fetch_post_like_user_list()
    {
        var fetch_data = '';
        var element = $(this);
        var post_id = element.data('post_id');
        var action = 'like_user_list';
        $.ajax({
            url:"action.php",
            method:"POST",
            async: false,
            data:{post_id:post_id, action:action},
            success:function(data)
            {
                fetch_data = data;
            }
        });
        return fetch_data;
    }
});*/


})
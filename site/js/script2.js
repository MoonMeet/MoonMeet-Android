function changeTheme() {
    var element = document.body;
   element.classList.toggle("changeBackground");
  }
  if ( window.history.replaceState ) {
    window.history.replaceState( null, null, window.location.href );
  }
  


$(document).ready(function(){
    navListener();


});

function navListener(){
    $("#menu-button").hover(function(){
        $("#menudrop").addClass("show");
    });


    $(".dropdown-menu").mouseleave(function () {
        $("#menudrop").removeClass("show");
    });
}





$(document).ready(function(){

    fetchPage(1);
    indexListener();

    $("#menu-button").hover(function(){
        $("#menudrop").addClass("show");
    });


    $(".dropdown-menu").mouseleave(function () {
        $("#menudrop").removeClass("show");
    });


    // var navbarToggle = '.navbar-toggle'; // name of navbar toggle, BS3 = '.navbar-toggle', BS4 = '.navbar-toggler'
    // $('.dropdown, .dropup').each(function() {
    //     var dropdown = $(this),
    //         dropdownToggle = $('[data-toggle="dropdown"]', dropdown),
    //         dropdownHoverAll = dropdownToggle.data('dropdown-hover-all') || false;
    //
    //     // Mouseover
    //     dropdown.hover(function(){
    //         var notMobileMenu = $(navbarToggle).size() > 0 && $(navbarToggle).css('display') === 'none';
    //         if ((dropdownHoverAll == true || (dropdownHoverAll == false && notMobileMenu))) {
    //             dropdownToggle.trigger('click');
    //         }
    //     })
    // });



});


function indexListener(){
    $(document).on("click", "#staticpagelinkdiv .staticpages", function(){
        $("#staticdiv").empty();
        var thisItem = $(this).attr("id");
        var itemNum = thisItem.substr(10, thisItem.length - 1);
        fetchPage(itemNum);
    });
}

function fetchPage(pageID){

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/displayStaticPage/" + pageID,
        dataType: "json",
        headers:{
            "Accept": "application/json",
            "Content-Type" : "application/json"
        },
        success: function(data){
            var post = "<div class='page-container'>" + data.body + "</div>";
            $("#staticdiv").html(post);
        },
        error: function(){
        }
    });

}
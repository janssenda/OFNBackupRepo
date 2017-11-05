$(document).ready(function () {

    indexListener();
    navListener();

});

function hideall(){
    $("#mainblogdiv").hide();
    $("#singleblogdiv").hide();
    $("#commentbuttondiv").hide();
    $("#staticdiv").hide();
}

// Listens for clicks on elements and implements resulting functions
function indexListener() {
    $(document).on("click", "#staticpagelinkdiv .staticpages", function () {
         hideall();
        $("#staticdiv").show();
        var thisItem = $(this).attr("id");
        var itemNum = thisItem.substr(10, thisItem.length - 1);
        fetchPage(itemNum);
    });

    $(document).on("click", "#mainblogdiv .blogposts", function () {
        fetchBlogPost($(this).attr("id"));
    });

    $('input[type=radio]').click(function () {
        var radioClicked = this.id;
        if (radioClicked === "staticPageRadio") {
            $("#blogOptions").hide();}
        else {
            $("#blogOptions").show();}
    });
}

// Fetches a page by ID and displays it
function fetchPage(pageID) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/displayStaticPage/" + pageID,
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (data) {
            var post = "<div class='page-container'>" + data.body + "</div>";
            $("#staticdiv").html(post); },
        error: function () {
            alert("fail"); }
    });
}

// Fetches a post by ID and displays it.  On successful retrieval,
// comments are also queried for and added.
function fetchBlogPost(blogPostID) {
    var singleblogdiv = $("#singleblogdiv");
    hideall();

    singleblogdiv.empty();
    singleblogdiv.show();

    blogPostID = blogPostID.substr(8, blogPostID.length - 1);
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/displayBlogPost/" + blogPostID,
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (data) {

            // Note the addition of moment.js.  Using the backend JSON
            // serializer for date, this greatly simplifies the date-time
            // format exchange from front to back.

            // var d = new moment(data.updateTime); parses the serialized date object into a moment.js type date
            // d.format("MMM Do YY, h:mm a") provides a simple method to format the date for output
            var d = new moment(data.updateTime);

            var post = "<div class='page-container'>" + data.title + "<br/>" +
                "Last updated on " + d.format("MMM Do YY, h:mm a")+
                "<br/>" + data.body + "<br/>Comments: <br/></div>";

            singleblogdiv.append(post);
            fetchComments(blogPostID);

        },
        error: function () {
            alert("fail");
        }
    });
}


// Grabs comments for a single blog post and attaches them
function fetchComments(blogPostID){
    $.ajax({
        type: "get",
        url: "http://localhost:8080/getCommentsForBlogPost/" + blogPostID,
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (commdata) {
            $.each(commdata, function (i) {
                $("#singleblogdiv").append(JSON.stringify(commdata[i]) + "<br>");
            });},
        error: function () {
            alert("fail"); }
    });
    $("#commentbuttondiv").show();
}
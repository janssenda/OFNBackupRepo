
$(document).ready(function () {
    clickHandlers()
});

function clickHandlers(){

    var category = $("#category");
    var subcategory = $("#subcategory");

    $("#searchbutton").click(function () {
        queryforresults();
    });

    category.change(function () {
        if (category.val() === "page"){$(".postopt").hide();}
        else {$(".postopt").show()}
        subcategory.val("general").prop('selected', true);
    });

    subcategory.change(function () {
        var elSub = subcategory.val();

        if (elSub === "date") {
            $("#searchterms").hide();
            $("#cat-select").hide();
            $("#searchdate").show();
        }else if (elSub === "category") {
            $("#searchterms").hide();
            $("#cat-select").show();
            $("#searchdate").hide();
        } else {
            $("#searchterms").show();
            $("#searchdate").hide();
            $("#cat-select").hide();
        }
    });

}


function queryforresults() {
    var terms;
    var category = $("#category").val();
    var method = $("#subcategory").val();

    if (method === "date") {terms = $("#searchdate").val();}
    else if (method === "category") {terms = $("#cat-select").val();}
    else {terms = $("#searchterms").val()}
    var state = $("#state").val();

    if (state === undefined){state = "published"}
    var call = "http://localhost:8080/search/" + category
        + "s?method=" + method + "&state=" + state + "&terms=" + terms;

    console.log(call);

    $.ajax({
        type: "GET",
        url: call,
        success: function (results) {
            printResults(results, category);
        },
        error: function () {
            alert("fail")
        }
    });
}


function printResults(results, category) {

    var id = "blogPostId";
    var time = "updateTime";
    var type = "blog";

    if (category === "page"){
        id = "pageId";
        time = "updatedTime";
        type = "page";
    }
    var sec = $("#sec").val();

    var resultstable =
        "<table id='resultstable'>" +
        "<thead> <tr> <th class='header-th-id'>ID</th> <th>Title</th> <th>Date</th> " +
        "<th>Preview</th>";

        if (sec === "true") {
            resultstable += " <th>Published</th> <th>Admin Functions</th> ";
        }

        resultstable += "</tr> </thead> <tbody>";

    $.each(results, function (index, res) {

        var d = moment(res[time]);
        var body = res.body;


        resultstable +=
            "<tr><td class='id-col'><a target='_blank' href='./show?contentType="+type+"&contentID="
            + res[id] + "'>" + res[id] + "</a></td>" +
            "<td><a target='_blank' href='./show?contentType="+type+"&contentID=" + res[id] + "'>"
            + res.title + "</a></td>" +
            "<td>" + d.format("MMM Do YY, h:mm a")  + "</td>" +
            "<td>" + getPreview(body, 4) + "</td>";

        if (sec === "true") {
            resultstable += "<td>" + res.published + "</td>" +
                "<td><a target='_blank' href='./editcontent?contentType=" + type + "&contentID=" + res[id] + "'>Edit</a>&nbsp; | &nbsp;";
            if (type === "blog") {
                resultstable += "<a target='_blank' href='deleteBlogPost?blogId=" + res[id] + "'>Delete</a></td>";
            }
        }
        resultstable += "</tr>";
    });

    resultstable += "</tbody> </table> <hr/>";
    $("#resultsdiv").html(resultstable);
}


function strip(html)
{
    var tmp = document.createElement("DIV");
    tmp.innerHTML = html;
    return tmp.textContent || tmp.innerText || "";
}


function getPreview(fulltext, wlim){
    fulltext = strip(fulltext);
    var words = fulltext.split(" ");
    var preview = "";

    for (var i = 0; i < wlim; i++){
        if (i === wlim - 1){
            preview += words[i]+"...";
        } else {
            preview += words[i] + " ";
        }
    }
    return preview;
}
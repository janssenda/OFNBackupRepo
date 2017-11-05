$(document).ready(function () {

launchEditor();

});


function launchEditor() {
    $('#newBlogPost').froalaEditor({

        // Set custom buttons with separator between them.
        toolbarButtons: ['undo', 'redo', '|', 'bold', 'italic', 'underline', 'strikeThrough',
            'subscript', 'superscript', 'outdent', 'indent',
            'clearFormatting', 'insertTable', 'html', 'insertLink',
            'insertImage', 'insertVideo', 'emoticons', 'specialCharacters',
            'fontFamily', 'fontSize', 'color','align', 'formatOL', 'formatUL' ],


        heightMin: 300,
        charCounterCount: false,
        theme: 'dark',
        zIndex: 2003
    });

}
$(function() {
    var isDragOver = false;

    var files = [];
    var currentFileName;

    var uploadQueue = [];
    var currentQueueIndex = 0;
    var isUploading = false;
    var getProgressReties = 0;

    var html5Uploader = null;
    var items = {};

    function initPageStrings() {
        document.title = STRINGS.WIFI_TRANS_TITLE;
        $('.content_title').text(STRINGS.FILES_ON_DEVICE);
        $('.table_header .filename').text(STRINGS.FILENAME);
        $('.table_header .size').text(STRINGS.FILE_SIZE);
        $('.table_header .operate').text(STRINGS.FILE_OPER);
    }

    function deleteBook(_event) {
        if (!confirm(STRINGS.CONFIRM_DELETE_BOOK)) {
            return;
        }
        var $node =  $(_event.currentTarget);
        var fileName = $node.siblings(':first').text();
        var deleteUrl = "delete_file";
        var fileInfoContainer = $node.parent();
        fileInfoContainer.css({ 'color':'#fff', 'background-color': '#cb4638' });
        fileInfoContainer.find('.trash').removeClass('trash').unbind();
        fileInfoContainer.find('.download').removeClass('download').unbind();
        $.post(deleteUrl, { '_method' : 'delete', 'file_name': fileName }, function() {
            setTimeout(function() {
                fileInfoContainer.slideUp('fast', function() {
                    fileInfoContainer.remove();
                });
            }, 300);
        });
    }

    function downloadBook(_event) {
        var $node =  $(_event.currentTarget);
        var fileName = $node.siblings(':first').text();
        var url = "files/" + fileName;
        window.location = url;
    }

    function loadFileList() {
        var now = new Date();
        var url = "files?";
        $.get(url + now.getTime(), function(data) {
            files = data;
            fillFilesContainer();
            //$(".download").click(downloadBook);
            //$(".trash").click(deleteBook);
        });
    }

    function fillFilesContainer() {
        var height = $(window).height() - $("#right .content_title").height() - $("#right .table_header").height();
        var filesContainer = $("#right .files");
        filesContainer.empty();
        //filesContainer.height(height);
        var rowsCount = Math.floor(height / 40);

        for (var i = 0; i < files.length;i ++) {
            var row = $('<div class="file"></div>');
            var fileInfo = files[i];
            row.append('<div class="column filename" filename="' + escape(fileInfo.name) + '">' + fileInfo.name +'</div>');
            row.append('<div class="column size">' + fileInfo.size + '</div>');
            row.append('<div class="column download" title="'+STRINGS.DOWNLOAD_FILE+'"></div>');
            row.append('<div class="column trash" title="'+STRINGS.DELETE_FILE+'"></div>');
            filesContainer.append(row);
        }

        return height;
    }

    function getUploadProgress() {
        var time = new Date().getTime();
        var url = 'progress/' + encodeURI(currentFileName) + '?' + time;
        $.getJSON(url, function(data) {
            if (!data) {
                getProgressReties ++
                if (getProgressReties < 5) {
                    setTimeout(getUploadProgress, 500);
                    return;
                } else {
                    alert(STRINGS.USE_ONE_BROWSER);
                }
            }

            getProgressReties = 0;
            var ele = $("#right .file [filename='" + escape(data.fileName) + "']")
            var eleSize = ele.next()
            eleSize.text(data.size);
            var elePrecent = eleSize.next()
            elePrecent.text(Math.round(data.progress * 100) + "%");
            var eleProgress = ele.prev();
            eleProgress.animate({ width:Math.round(483 * data.progress) }, 280);

            if (data.progress < 1) {
                setTimeout(getUploadProgress, 300);
            } else {
                elePrecent.text('');
            }
        });
    }

    function startAjaxUpload() {
        if (isUploading || currentQueueIndex >= uploadQueue.length) {
            return;
        }

        isUploading = true;
        var eleFile = $(uploadQueue[currentQueueIndex]);
        var eleFileId = eleFile.attr('id');
        var fileName = eleFile.val();
        var arr = fileName.split("\\");
        fileName = arr[arr.length - 1];

        currentQueueIndex ++;

        var row = $("#right .file [filename='" + escape(fileName) + "']").parent();

        alert(fileName)
        $.post( "/send_file_name", {"filename": fileName}, function() {
            $.ajaxFileUpload({
                url:'files',
                secureuri:false,
                fileElementId:eleFileId,
                dataType: 'text',
                success: function (data, status) {
                    row.removeClass('progress_wrapper');
                    row.find('.progress').remove();
                    row.find('.precent').text('').remove();
                    $('<div class="column download" title="'+STRINGS.DOWNLOAD_FILE+'"></div>')
                    //.click(downloadBook)
                        .appendTo(row);
                    $('<div class="column trash" title="'+STRINGS.DELETE_FILE+'"></div>')
                    //.click(deleteBook)
                        .appendTo(row);
                    isUploading = false;

                    //IE的诡异情况
                    row.find('.download').text('');
                    startAjaxUpload();
                },
                error: function (data, status, e) {
                    isUploading = false;
                    alert(STRINGS.UPLOAD_FAILED);
                    row.remove();
                    startAjaxUpload();
                }
            });

            currentFileName = fileName;
            // setTimeout(getUploadProgress, 300);
        });
    }

    function checkFileName(fileName) {
        if (!fileName || !fileName.toLowerCase().match('(epub|txt|pdf)$')) {
            return STRINGS.UNSUPPORTED_FILE_TYPE;
        }

        var arr = fileName.split("\\");
        fileName = arr[arr.length - 1];

        var hasFile = false;
        var existFile = $("#right .file [filename='" + escape(fileName) + "']");
        if (existFile.length > 0) {
            $(this).val("");
            if (existFile.parent().hasClass('progress_wrapper')) {
                return STRINGS.FILE_IN_QUEUE;
            } else {
                return STRINGS.FILE_EXISTS;
            }
        }
        return null;
    }

    function uploadFiles(files) {
        var uploader = getHtml5Uploader();
        if (files.length == 1) {
            var msg = checkFileName(files[0].name || files[0].fileName);
            if (msg) {
                alert(msg);
                return;
            }
            uploader.add(files[0]);
            return;
        }

        var totalFiles = files.length;
        var actualFiles = 0;
        for (var i = 0; i < files.length; ++i) {
            if (!checkFileName(files[i].name || files[i].fileName)) {
                uploader.add(files[i]);
                actualFiles ++;
            }
        }
        if (totalFiles != actualFiles) {
            var msg = STRINGS.YOU_CHOOSE + totalFiles + STRINGS.CHOSEN_FILE_COUNT + actualFiles + STRINGS.VALID_CHOSEN_FILE_COUNT;
            alert(msg);
        }
    }

    function bindAjaxUpload(fileSelector) {
        $(fileSelector).unbind();
        $(fileSelector).change(function() {
            if (this.files) {
                uploadFiles(this.files)
                //优先使用HTML5上传方式
                return;
            }

            var fileName = $(this).val()

            var msg = checkFileName(fileName);
            if (msg) {
                alert(msg);
                return;
            }

            var arr = fileName.split("\\");
            fileName = arr[arr.length - 1];

            var row = $('<div class="file progress_wrapper"></div>');
            row.append('<div class="progress"></div>');
            row.append('<div class="column filename" filename="' + escape(fileName) + '">' + fileName +'</div>');
            row.append('<div class="column size"> - </div>');
            row.append('<div class="column precent">0%</div>');
            $("#right .files").prepend(row);

            uploadQueue.push(fileSelector);
            $(fileSelector).css({ top: '-9999px', left: '-9999px' });
            $('.file_upload_warper').append('<input type="file" name="newfile" value="" id="newfile_' + uploadQueue.length + '" class="file_upload" />');
            bindAjaxUpload('#newfile_' + uploadQueue.length);
            startAjaxUpload();
        });

        $(fileSelector)
            .mouseover(function() { $('#upload_button').removeClass('normal').addClass('pressed'); })
            .mouseout(function() { $('#upload_button').removeClass('pressed').addClass('normal'); })
            .mousedown(function() { $('#upload_button').removeClass('normal').addClass('pressed'); })
            .mouseup(function() { $('#upload_button').removeClass('pressed').addClass('normal'); });
        // if (typeof(Worker) !== "undefined") {
        //     $(fileSelector)
        //         .mouseover(function() { $('#upload_button').removeClass('normal').addClass('pressed'); })
        //         .mouseout(function() { $('#upload_button').removeClass('pressed').addClass('normal'); })
        //         .mousedown(function() { $('#upload_button').removeClass('normal').addClass('pressed'); })
        //         .mouseup(function() { $('#upload_button').removeClass('pressed').addClass('normal'); });
        // } else {
        //     $(fileSelector)
        //         .mouseover(function() { $('#upload_button').css('background-image', 'url("images/select_file1_rollover.jpg")'); })
        //         .mouseout(function() { $('#upload_button').css('background-image', 'url("images/select_file1.jpg")'); })
        //         .mousedown(function() { $('#upload_button').css('background-image', 'url("images/select_file1_pressed.jpg")'); })
        //         .mouseup(function() { $('#upload_button').css('background-image', 'url("images/select_file1.jpg")'); });
        // }
    }

    function formatFileSize(value) {
        var multiplyFactor = 0;
        var tokens = ["bytes","KB","MB","GB","TB"];

        while (value > 1024) {
            value /= 1024;
            multiplyFactor++;
        }

        return value.toFixed(1) + " " + tokens[multiplyFactor];
    }

    function cancelUpload() {
        var uploader = getHtml5Uploader();
        var fileName = $(this).parent().find('.filename').attr('filename');
        if (fileName) {
            item = items[fileName];
            if (item) {
                uploader.abort(item);
            }
        }
    }

    function getHtml5Uploader() {
        if (!html5Uploader) {
            html5Uploader = new bitcandies.FileUploader({
                url: 'files',
                maxconnections: 1,
                fieldname: 'newfile',
                enqueued: function (item) {
                    var fileName = item.getFilename();
                    items[escape(fileName)] = item;
                    var size = item.getSize();
                    var row = $('<div class="file progress_wrapper"></div>');
                    row.append('<div class="progress"></div>');
                    row.append('<div class="column filename" filename="' + escape(fileName) + '">' + fileName +'</div>');
                    row.append('<div class="column size">' + formatFileSize(size) +'</div>');
                    row.append('<div class="column precent">0%</div>');
                    $('<div class="column trash_white" title="'+STRINGS.CANCEL+'"></div>')
                        .click(cancelUpload)
                        .appendTo(row);
                    $("#right .files").prepend(row);
                },
                progress: function (item, loaded, total) {
                    var fileName = item.getFilename();
                    var progress = loaded / total;

                    var ele = $("#right .file [filename='" + escape(fileName) + "']")
                    var elePrecent = ele.next().next();
                    elePrecent.text(Math.round(progress * 100) + "%");
                    var eleProgress = ele.prev();
                    eleProgress.width(483 * progress);
                },
                success: function (item) {
                    var fileName = item.getFilename();
                    var row = $("#right .file [filename='" + escape(fileName) + "']").parent();

                    row.removeClass('progress_wrapper');
                    row.find('.progress').remove();
                    row.find('.precent').text('').remove();
                    row.find('.trash_white').remove();
                    $('<div class="column download" title="'+STRINGS.DOWNLOAD_FILE+'"></div>')
                    //.click(downloadBook)
                        .appendTo(row);
                    $('<div class="column trash" title="'+STRINGS.DELETE_FILE+'"></div>')
                    //.click(deleteBook)
                        .appendTo(row);
                },
                error: function (item) {
                    var fileName = item.getFilename();
                    var row = $("#right .file [filename='" + escape(fileName) + "']").parent();
                    row.remove();
                },
                aborted: function (item) {
                    var fileName = item.getFilename();
                    var row = $("#right .file [filename='" + escape(fileName) + "']").parent();
                    row.remove();
                }
            });
        }
        return html5Uploader;
    }

    function handleDragOver(evt) {
        evt.stopPropagation();
        evt.preventDefault();
        if (!isDragOver) {
            $(this).removeClass('normal').addClass('active');
            isDragOver = true;
        }
    }

    function handleDragLeave(evt) {
        evt.stopPropagation();
        evt.preventDefault();

        $(this).removeClass('active').addClass('normal');
        isDragOver = false;
    }

    function handleDrop(evt) {
        evt.stopPropagation();
        evt.preventDefault();
        var uploader = getHtml5Uploader();

        $(this).removeClass('active').addClass('normal');
        isDragOver = false;

        if (evt.dataTransfer && evt.dataTransfer.files) {
            uploadFiles(evt.dataTransfer.files);
        }
    }

    function dragUpload() {
        var dropArea = document.getElementById('drag_area');
        if (dropArea && dropArea.addEventListener) {
            dropArea.addEventListener('dragover', handleDragOver, false);
            dropArea.addEventListener('dragleave', handleDragLeave, false);
            dropArea.addEventListener('drop', handleDrop, false);
        }
    }

    function showHtml5View() {
        // $('#logo').append('<img src="images/logo5.jpg" />');

        // var dragAree = $('<div id="drag_area"><div class="drag_hint">' + STRINGS.DRAG_TO_HERE + '</div></div)').addClass('normal').appendTo('#logo');
        // $('#upload_hint').empty();
        // $('<div>'+STRINGS.SELECT_YOUR_FILES+'<br />'+STRINGS.SUPPORTED_FILE_TYPES+'</div>')
        //     .addClass('hint')
        //     .insertAfter('#upload_button');

        // var buttonLabels = $('<div class="button_lables"></div>').prependTo("#upload_button")
        // $('<div class="button_lable1"></div>').text(STRINGS.SELECT_BUTTON_LABLE1).appendTo(buttonLabels);
        // $('<div class="button_lable2"></div>').text(STRINGS.SELECT_BUTTON_LABLE2).appendTo(buttonLabels);

        dragUpload();
    }

    function showHtml4View() {
        // $('#logo').append('<img src="images/logo.jpg" />').append('<div class="wifi">' + STRINGS.WIFI_AVAILABLE + '</div>');
        // $('#upload_button').css('background-image', 'url("images/select_file1_rollover.jpg")');
        // $('#upload_hint').html(STRINGS.SELECT_YOUR_FILES+'<br />'+STRINGS.SUPPORTED_FILE_TYPES);
        // $('<div class="button_lable">' + STRINGS.SELECT_BUTTON_LABLE + '</div>').prependTo("#upload_button")
    }

    $(document).ready(function() {
        // events delegate
        $('.files').on('click', '.trash', deleteBook);
        $('.files').on('click', '.download', downloadBook);

        initPageStrings();
        fillFilesContainer();
        loadFileList();
        $(window).resize(function() {
            fillFilesContainer();
        });
        bindAjaxUpload('#newfile_0');

        if (typeof(Worker) !== "undefined") {
            showHtml5View();
        } else {
            showHtml4View();
        }

        $(document).ajaxError(function(event, request, settings){
            alert(STRINGS.CANNOT_CONNECT_SERVER);
        });
    });
});

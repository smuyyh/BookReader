/**
* Upload5 0.1.0
* (c) 2011 BitCandies
* http://github.com/bitcandies/upload5
*
* Upload5 is a flexible HTML5/JavaScript library that handles multiple file uploads.
*
* Upload5 is freely distributable under the MIT license,
* see LICENSE.
*
* http://www.bitcandies.com/
*/

var bitcandies = bitcandies || {};

bitcandies.defaults = function (dest, source) {
    for (var i in source) {
        if (typeof dest[i] == 'undefined') {
            dest[i] = source[i];
        }
    }
};

bitcandies.FileUploader = function (options) {
    var default_options = {
        fieldname: 'file',
        maxconnections: 2,
        //maxfilesize: 1024,
        method: 'POST',
        default_params: {},
        url: '',

        enqueued: function (item) { },
        aborted: function (item) { },
        start: function (item, xhr) { },
        progress: function (item, loaded, total, xhr) { },
        success: function (item, xhr) { },
        error: function (item, xhr) { },
        complete: function (item, xhr) { }
    };
    this.options = options || {};
    bitcandies.defaults(this.options, default_options);

    this.queue = [];
    this.running = [];
    this.completed = [];

    this._curid = 1;
};

bitcandies.FileUploader.Statuses = {
    QUEUED: 'queued',
    ABORTED: 'aborted',
    UPLOADING: 'uploading',
    COMPLETED: 'completed'
};

bitcandies.FileUploader.QueuedFile = function (uploader, file, params, id) {
    if (!(file instanceof File)) {
        throw new Error('Cannot add a non-File object to upload queue.');
    }
    this.uploader = uploader;
    this.file = file;
    this.params = params || {};
    this.id = id;
    this.status = bitcandies.FileUploader.Statuses.QUEUED;
}

bitcandies.FileUploader.QueuedFile.prototype = {
    getFilename: function () {
        return this.file.fileName ? this.file.fileName : this.file.name;
    },

    getSize: function () {
        return this.file.fileSize ? this.file.fileSize : this.file.size;
    },

    getFile: function () {
        return this.file;
    },

    getParams: function () {
        return this.params;
    },

    getStatus: function () {
        return this.status;
    }
};

bitcandies.FileUploader.prototype = {
    add: function (file, params) {
        params = params || {};
        bitcandies.defaults(params, this.options.default_params);
        var item = new bitcandies.FileUploader.QueuedFile(this, file, params, this._curid);
        this._curid++;
        this.queue.push(item);
        this.options.enqueued.call(this, item);
        this.run();
        return item;
    },

    abort: function (item) {
        if (item.status !== bitcandies.FileUploader.Statuses.COMPLETED && item.status !== bitcandies.FileUploader.Statuses.ABORTED) {
            for (var i = 0; i < this.queue.length; ++i) {
                if (item.id === this.queue[i].id) {
                    this.queue.splice(i, 1);
                    item.status = bitcandies.FileUploader.Statuses.ABORTED;
                    this.options.aborted.call(this, item);
                    return;
                }
            }
            for (var i = 0; i < this.running.length; ++i) {
                if (item.id === this.running[i].id) {
                    item.status = bitcandies.FileUploader.Statuses.ABORTED;
                    item.xhr.abort();
                    this.options.aborted.call(this, item);
                    return;
                }
            }
        }
    },

    doUpload: function (item) {
        var self = this,
            xhr = new XMLHttpRequest(),
            formData = new FormData();

        item.status = bitcandies.FileUploader.Statuses.UPLOADING;
        item.xhr = xhr;
        console.log("debug file name" + item.file.name);

        $.post( "/send_file_name", {"filename": item.file.name}, function() {
            
            self.options.start.call(self, item, xhr);

            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    self.onComplete(item, xhr);
                }
            };

            xhr.upload.onprogress = function (e) {
                if (e.lengthComputable) {
                    self.options.progress.call(self, item, e.loaded, e.total, xhr);
                }
            };

            xhr.open(self.options.method, self.options.url, true);

            for (var key in item.params) {
                formData.append(key, item.params[key]);
            }
            formData.append(self.options.fieldname, item.file);

            xhr.send(formData);
        });
    },

    isRunning: function () {
        return this.queue.length || this.running.length;
    },

    onComplete: function (item, xhr) {
        if (item.status !== bitcandies.FileUploader.Statuses.ABORTED) {
            item.status = bitcandies.FileUploader.Statuses.COMPLETED;
            this.options.progress.call(this, item, item.getSize(), item.getSize(), xhr);
            if (xhr.status == 200) {
                this.options.success.call(this, item, xhr);
            }
            else {
                this.options.error.call(this, item, xhr);
            }
            this.options.complete.call(this, item, xhr);
        }
        if (item.xhr) {
            delete item.xhr;
        }

        for (var i in this.running) {
            if (this.running[i].id === item.id) {
                this.running.splice(i, 1);
                break;
            }
        }
        this.run();
    },

    run: function () {
        if (this.queue.length > 0 && this.running.length < this.options.maxconnections) {
            for (var i = 0; i < this.options.maxconnections - this.running.length; ++i) {
                var item = this.queue.shift();
                this.running.push(item);
                this.doUpload(item);
            }
        }
    }
};

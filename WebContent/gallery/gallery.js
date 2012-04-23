/*!
  swipeGallery 0.6.0
  Examples and documentation at: 
  http://www.app-agentur-bw.de/showcase/swipegallery
  2011 AREA-NET GmbH (www.app-agentur-bw.de | www.area-net.de)
  Version: 0.5.0 (17-MARCH-2011)
  Dual licensed under the MIT and GPL licenses:
  http://www.opensource.org/licenses/mit-license.php
  http://www.gnu.org/licenses/gpl.html
  
  Requires:
  jQuery v1.3.2 or later
  
 */
(function ($) {
  $.fn.swipeGallery = function (options) {
    var settings = {
      'classname': 'appGallery',
      'autoHeight': false,
      'height': '600px',
      'width': '800px',
      'background': '#000000',
      'tolerance': 0.25,
      'debug': false,
      'delay': 300
    }
    var mousedown = false;
    var mouseX = 0;
    var imagesLength = 0;
    var imagesCurrent = 0;
    var xdiff = 0;
    var containerHeight = 0;
    var containerWidth = 0;
    var debugBar = null;
    var touchstart = 0;
    var isInit = false;
    function doResizeImage(listElement) {
      $(listElement).css('height', containerHeight);
      $(listElement).css('width', containerWidth);
      var img = $(listElement).find('img');
      if (img.width() / containerWidth > img.height() / containerHeight) {
        img.width(containerWidth);
        var top = (containerHeight - img.height()) / 2;
        img.css('marginTop', top + 'px');
      } else {
        img.height(containerHeight);
        var left = parseInt((containerWidth - img.width()) / 2);
        img.css('marginLeft', left + 'px');
      }
    }

    function init(obj, parent, imageHandler) {
      if (isInit) return;
      isInit = true;
      imagesLength = 0;
      if (settings.autoHeight) {
        containerHeight = $(window).height();
        containerWidth = $(window).width();
      } else {
        containerHeight = parseInt(settings.height);
        containerWidth = parseInt(settings.width);
      }
      obj.find('li').each(function () {
        doResizeImage(this);
        imagesLength++;
      })
      parent.css('height', containerHeight);
      parent.css('width', containerWidth);
      imageHandler.css('width', containerWidth);
      imageHandler.css('height', containerHeight);
      imageHandler.css('left', parent.position().left);
      imageHandler.css('top', parent.position().top);
      obj.css('width', imagesLength * containerWidth);
    }

    function debug(text) {
      if (debugBar) {
        debugBar.html('<b>DEBUG-BAR: </b>' + text);
      }
    }
    return this.each(function () {
      var _this = $(this);
      if (options) {
        $.extend(settings, options);
      }
      if (settings.autoHeight) {
        containerHeight = $(window).height();
        containerWidth = $(window).width();
      } else {
        containerHeight = parseInt(settings.height);
        containerWidth = parseInt(settings.width);
      }
      _this.wrap('<div class="' + settings.classname + '"/>');
      var parent = _this.parent();
      parent.css('backgroundColor', settings.background);
      parent.prepend('<div class="imageHandler"/>');
      if (settings.debug) {
        parent.prepend('<div class="swipeDebug" style="font-family:Arial;font-size:11px;text-align:center;position:relative;z-index:200;padding:5px;background-color:#FFFFFF;"><b>DEBUG-BAR</b></div>');
        debugBar = _this.parent().find('.swipeDebug');
      }
      var imageHandler = _this.parent().find('.imageHandler');
      init(_this, parent, imageHandler);
      $(window).resize(function () {
        isInit = false;
        init(_this, parent, imageHandler);
      })
      imageHandler.bind('touchstart', function (event) {
        event.preventDefault();
        var touch = event.originalEvent.touches[0] || event.originalEvent.changedTouches[0];
        if (!this.mousedown) {
          this.mousedown = true;
          this.mouseX = touch.pageX;
        }
        return false;
      });
      imageHandler.bind('touchmove', function (event) {
        event.preventDefault();
        var touch = event.originalEvent.touches[0] || event.originalEvent.changedTouches[0];
        if (this.mousedown) {
          xdiff = touch.pageX - this.mouseX;
          _this.css('left', -imagesCurrent * containerWidth + xdiff);
        }
        return false;
      });
      imageHandler.bind('touchend', function (event) {
        event.preventDefault();
        var touch = event.originalEvent.touches[0] || event.originalEvent.changedTouches[0];
        this.mousedown = false;
        if (!xdiff) return false;
        var fullWidth = parseInt(settings.width);
        var halfWidth = fullWidth / 2;
        if (-xdiff > halfWidth - fullWidth * settings.tolerance) {
          imagesCurrent++;
          debug(imagesCurrent + ' = ' + imagesLength);
          imagesCurrent = imagesCurrent >= imagesLength ? imagesLength - 1 : imagesCurrent;
          _this.animate({
            left: -imagesCurrent * containerWidth
          }, settings.delay);
        } else if (xdiff > halfWidth - fullWidth * settings.tolerance) {
          imagesCurrent--;
          imagesCurrent = imagesCurrent < 0 ? 0 : imagesCurrent;
          _this.animate({
            left: -imagesCurrent * containerWidth
          }, settings.delay);
        } else {
          _this.animate({
            left: -imagesCurrent * containerWidth
          }, settings.delay);
        }
        xdiff = 0;
        return false;
      });
      imageHandler.mousedown(function (event) {
        if (!this.mousedown) {
          this.mousedown = true;
          this.mouseX = event.pageX;
        }
        return false;
      });
      imageHandler.mousemove(function (event) {
        if (this.mousedown) {
          xdiff = event.pageX - this.mouseX;
          _this.css('left', -imagesCurrent * containerWidth + xdiff);
        }
        return false;
      });
      imageHandler.mouseup(function (event) {
        this.mousedown = false;
        if (!xdiff) return false;
        var fullWidth = parseInt(settings.width);
        var halfWidth = fullWidth / 2;
        debug(xdiff + ' > ' + halfWidth + ' - ' + fullWidth + ' * ' + settings.tolerance);
        if (-xdiff > halfWidth - fullWidth * settings.tolerance) {
          imagesCurrent++;
          imagesCurrent = imagesCurrent >= imagesLength ? imagesLength - 1 : imagesCurrent;
          _this.animate({
            left: -imagesCurrent * containerWidth
          }, settings.delay);
        } else if (xdiff > halfWidth - fullWidth * settings.tolerance) {
          imagesCurrent--;
          imagesCurrent = imagesCurrent < 0 ? 0 : imagesCurrent;
          _this.animate({
            left: -imagesCurrent * containerWidth
          }, settings.delay);
        } else {
          _this.animate({
            left: -imagesCurrent * containerWidth
          }, settings.delay);
        }
        xdiff = 0;
        return false;
      });
      imageHandler.mouseleave(function (event) {
        imageHandler.mouseup();
      })
    });
  };
})(jQuery);
$(document).ready(function() {  
  // 字級大中小
    $('.change-font').on('click', 'a', function (e) {
    e.preventDefault();
    if ($(this).hasClass('font-size-small')) {
        $('body').removeClass('middle large').addClass('small');
        $('.smart_iframe').contents().find('body').css('font-size','95%');
    } else if ($(this).hasClass('font-size-middle')) {
        $('body').removeClass('small large').addClass('middle');
        $('.smart_iframe').contents().find('body').css('font-size','100%');
    } else if ($(this).hasClass('font-size-large')) {
        $('body').removeClass('small middle').addClass('large');
        $('.smart_iframe').contents().find('body').css('font-size','105%');
    }
});

$('.change-font a').click(function(){
    $(".active").removeClass("active");     
    $(this).addClass("active");
});  

  // 手機時主選單展開
  // 右上工具列點擊主選單隱藏
    $('.navbar-tool a').click(function(){ 
      $('.navbar-collapse.show').collapse('hide');
    });
    
   
  // 手機時點黑幕右上工具關閉
  $('.overlay').click(function(){ 
    $('.navbar-collapse.show').collapse('hide');
  });
  
  
  });
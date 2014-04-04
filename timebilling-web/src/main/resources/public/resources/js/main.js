function loadServices(projectId, pageNumber){
	pageNumber = pageNumber == null ? 0 : pageNumber;
	jQuery.get($("#newServiceForm").attr("action"), {
		project : projectId,
		page : pageNumber,
		size : 5
	}, function(data) {

		$("#templates").load("/templates #template-services",function(){
	          	var template = document.getElementById('template-services').innerHTML;
		  		Mustache.parse(template);   // optional, speeds up future uses
				var rendered = Mustache.render(template, data);
				
				if(data.firstPage){
					$("#servicesTable").html(rendered);
				}else{
					$("#servicesTable").append(rendered);
				}

				initEdit($('.post:not(.add-new-post, .disabled)'));
				
				var moreAction = $("#tab-spent > .more-posts");		
				moreAction.off();
				
				if(data.lastPage){
					moreAction.hide();
				}else{					
					moreAction.on('click',
							function(e) {
								loadServices(projectId, data.number + 1);
								e.preventDefault();
							});
					moreAction.show();

				}
				
		});
		

	});
}

function initSortable() {
	$('.project-items').sortable({
		handle : '.sorting'
	});

}

function initTabs() {
	var btns = $('#tabs .add-record');
	$('.tab-list a').click(
			function(e) {
				var tabID = $(this).attr('href').substr(1), btn = btns
						.filter('[data-ref=' + tabID + ']');

				btns.addClass('hidden');
				btn.removeClass('hidden');

				$(this).tab('show');
				e.preventDefault();
			});
}

function initAddingNewPost() {
	$('.add-record')
			.each(
					function() {
						var btnAdd = $(this), tabID = btnAdd.data('ref'), currentTab = $('#'
								+ tabID), addForm = currentTab
								.find('.add-new-post .row-form'), btnSave = addForm
								.find('.save'), btnCancel = addForm
								.find('.delete');

						btnAdd.on('click', function(e) {
							addForm.show();
							btnAdd.hide();
							e.preventDefault();
						});

						btnCancel.on('click', function(e) {
							addForm.hide();
							btnAdd.show();
							e.preventDefault();
						});

						btnSave.on('click', function(e) {
							console.log('save');
						});

					});
}

function initEdit(els) {
	els
			.each(function() {
				var currentPost = $(this), icon = currentPost.find('.icon > a'), btnRemove = currentPost
						.find('.buttom-box > .delete'), btnSave = currentPost
						.find('.buttom-box > .save'), dateVal = currentPost
						.find('.row-content .date'), timeVal = currentPost
						.find('.row-content .time'), commentVal = currentPost
						.find('.row-content .comment'), fieldDay = currentPost
						.find('input.day'), fieldMonth = currentPost
						.find('input.month'), fieldYear = currentPost
						.find('input.year'), fieldTime = currentPost
						.find('.time-field input'), fieldId = currentPost
						.find('input[type=hidden]'), fieldComment = currentPost
						.find('textarea'), datepickerHolder = currentPost
						.find('.calendar-holder');

				icon.on('click', function(e) {
					var d = dateVal.attr('data-selected-date'), dateParts = d
							.split('/');
					fieldTime.val(timeVal.text());
					fieldComment.val(commentVal.text());
					datepickerHolder.datepicker('setDate', d);
					fieldDay.val(dateParts[0]);
					fieldMonth.val(dateParts[1]);
					fieldYear.val(dateParts[2]);
					currentPost.addClass('editing');

					e.preventDefault();
				});

				btnRemove.on('click', function(e) {
					// currentPost.remove();
					currentPost.removeClass('editing');

					e.preventDefault();
				});

				btnSave.on('click', function(e) {

					var json = {
						"id" : fieldId.val(),
						"spentTime" : fieldTime.val(),
						"comment" : fieldComment.val(),
						"day" : fieldDay.val(),
						"month" : fieldMonth.val(),
						"year" : fieldYear.val()
					};

					jQuery.ajax({
						url : $("#newServiceForm").attr("action"),
						data : JSON.stringify(json),
						type : "PUT",
						contentType : 'application/json',
						success : function(createdService) {
							// loadServices();

							currentPost.removeClass('editing');
							commentVal.text(fieldComment.val());
							timeVal.text(fieldTime.val());

							dateVal.text($.datepicker.formatDate(
									'd MM yy',
									new Date(fieldYear.val() + '-'
											+ fieldMonth.val() + '-'
											+ fieldDay.val())).toLowerCase()); // datepickerHolder.datepicker('getDate')
							dateVal.attr('data-selected-date', $.datepicker
									.formatDate('dd/mm/yy', new Date(fieldYear
											.val()
											+ '-'
											+ fieldMonth.val()
											+ '-'
											+ fieldDay.val()))); // datepickerHolder.datepicker('getDate')

						}
					});

					e.preventDefault();
				});
			});
}

function initDatepicker(els) {
	$.datepicker.regional['ru'] = {
		closeText : 'Закрыть',
		prevText : '&#x3C;Пред',
		nextText : 'След&#x3E;',
		currentText : 'Сегодня',
		monthNames : [ 'Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
				'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь' ],
		monthNamesShort : [ 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл',
				'Авг', 'Сен', 'Окт', 'Ноя', 'Дек' ],
		dayNames : [ 'воскресенье', 'понедельник', 'вторник', 'среда',
				'четверг', 'пятница', 'суббота' ],
		dayNamesShort : [ 'вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт' ],
		dayNamesMin : [ 'Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб' ],
		weekHeader : 'Нед',
		dateFormat : 'dd.mm.yy',
		firstDay : 1,
		isRTL : false,
		showMonthAfterYear : false,
		yearSuffix : ''
	};
	$.datepicker.setDefaults($.datepicker.regional['ru']);

	els
			.each(function() {
				var currentItem = $(this), fieldDay = currentItem.find('.day'), fieldMonth = currentItem
						.find('.month'), fieldYear = currentItem.find('.year'), icon = currentItem
						.find('.icon-calendar'), datepickerHolder = currentItem
						.find('.calendar-holder');

				datepickerHolder.datepicker({
					dateFormat : 'dd/mm/yy',
					onSelect : function(dateText, inst) {
						var m = inst.currentMonth + 1;
						if (m < 10) {
							m = '0' + m;
						}
						fieldDay.val(inst.currentDay);
						fieldMonth.val(m);
						fieldYear.val(inst.currentYear);

						datepickerHolder.hide();
					}
				});

				icon.on('click', function(e) {
					if (datepickerHolder.is(':visible')) {
						datepickerHolder.hide();
					} else {
						datepickerHolder.show();
					}

					e.preventDefault();
				});
			});
}
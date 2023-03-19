<%@ include file="commons/header.jspf"%>
<%@ include file="commons/navigation.jspf"%>
<div class="container">
	<h1>Enter Todo Details</h1>

	<form:form method="post" modelAttribute="todo">

		<fieldset class="mb-3">
			<form:label path="description">Description</form:label>
			<form:input path="description" />
			<form:errors path="description" cssClass="text-warning" />
		</fieldset>

		<fieldset class="mb-3">
			<form:label path="targetDate">Target Date</form:label>
			<form:input path="targetDate" />
			<form:errors path="targetDate" cssClass="text-warning" />
		</fieldset>

		<form:input type="hidden" path="done" />

		<form:input type="hidden" path="id" />

		<input type="submit" class="btn btn-success" />
	</form:form>
</div>
<%@ include file="commons/footer.jspf"%>
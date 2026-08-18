document.addEventListener('DOMContentLoaded', function() {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const durationInput = document.getElementById('duration');
    const priceInput = document.getElementById('totalPrice');
    const dateError = document.getElementById('dateError');

    if (!startDateInput || !endDateInput) return;

    function updateDuration() {
        const start = new Date(startDateInput.value);
        const end = new Date(endDateInput.value);

        if (start && end) {
            const diffTime = Math.abs(end - start);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
            if (durationInput) durationInput.value = Math.max(1, diffDays);
            
            if (priceInput && bookingForm.dataset.dailyRate) {
                priceInput.value = Math.round(diffDays * parseFloat(bookingForm.dataset.dailyRate) * 100) / 100;
            }
        }
        
        if (dateError) dateError.textContent = '';
    }

    function validateDates() {
        const start = new Date(startDateInput.value);
        const end = new Date(endDateInput.value);

        if (start && end) {
            if (start >= end) {
                if (dateError) {
                    dateError.textContent = 'End date must be after start date';
                }
                return false;
            }
            if (dateError) dateError.textContent = '';
            return true;
        }
        if (dateError) dateError.textContent = '';
        return true;
    }

    if (startDateInput && endDateInput) {
        startDateInput.addEventListener('change', updateDuration);
        endDateInput.addEventListener('change', updateDuration);
    }

    const bookingForm = document.querySelector('form');
    if (bookingForm && priceInput) {
        bookingForm.dataset.dailyRate = priceInput.dataset.dailyRate || '25.00';
    }

    validateDates();
});
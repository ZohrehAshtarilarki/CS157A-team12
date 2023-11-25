document.addEventListener('DOMContentLoaded', (event) => {
    let stars = document.querySelectorAll('.star');
    let ratingInput = document.getElementById('ratingInput');

    stars.forEach(star => {
        star.addEventListener('click', function(ev) {
            let currentStar = ev.currentTarget;
            let rating = parseInt(currentStar.dataset.value);
            ratingInput.value = rating; // Set the rating input value

            stars.forEach(innerStar => {
                innerStar.style.color = rating >= parseInt(innerStar.dataset.value) ? 'gold' : 'gray';
            });
        });
    });
});

function submitRating() {
    // Make sure the rating is not zero (no star selected)
    if (document.getElementById('ratingInput').value !== '0') {
        document.getElementById('ratingForm').submit();
    } else {
        alert('Please select a rating before submitting.');
    }
}

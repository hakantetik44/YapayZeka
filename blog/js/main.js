// Smooth Scroll
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

// Typing Effect
const typingText = document.querySelector('.typing-text');
const text = typingText.textContent;
typingText.textContent = '';

let i = 0;
function type() {
    if (i < text.length) {
        typingText.textContent += text.charAt(i);
        i++;
        setTimeout(type, 100);
    }
}
type();

// Scroll Progress Bar
window.onscroll = function() {
    let winScroll = document.body.scrollTop || document.documentElement.scrollTop;
    let height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    let scrolled = (winScroll / height) * 100;
    document.querySelector(".progress-bar").style.width = scrolled + "%";
};

// Dark Mode Toggle
const darkModeToggle = document.querySelector('.dark-mode-toggle');
const body = document.body;

if (localStorage.getItem('darkMode') === 'enabled') {
    body.classList.add('dark-mode');
}

darkModeToggle.addEventListener('click', () => {
    body.classList.toggle('dark-mode');
    if (body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', null);
    }
});

// Mobile Menu Toggle
const menuToggle = document.querySelector('.menu-toggle');
const nav = document.querySelector('nav ul');

menuToggle.addEventListener('click', () => {
    nav.classList.toggle('active');
    menuToggle.classList.toggle('active');
});

// GitHub Activity Feed
async function fetchGitHubActivity() {
    const username = 'YOUR_GITHUB_USERNAME';
    try {
        const response = await fetch(`https://api.github.com/users/${username}/events/public`);
        const data = await response.json();
        const activityContainer = document.querySelector('.github-activity');
        
        data.slice(0, 5).forEach(event => {
            const activityItem = document.createElement('div');
            activityItem.classList.add('activity-item');
            activityItem.innerHTML = `
                <i class="fab fa-github"></i>
                <span>${formatGitHubEvent(event)}</span>
                <small>${new Date(event.created_at).toLocaleDateString()}</small>
            `;
            activityContainer.appendChild(activityItem);
        });
    } catch (error) {
        console.error('GitHub API Error:', error);
    }
}

function formatGitHubEvent(event) {
    switch(event.type) {
        case 'PushEvent':
            return `Pushed to ${event.repo.name}`;
        case 'CreateEvent':
            return `Created ${event.payload.ref_type} ${event.repo.name}`;
        case 'WatchEvent':
            return `Starred ${event.repo.name}`;
        default:
            return `Activity on ${event.repo.name}`;
    }
}

// Skill Progress Bars Animation
const skillBars = document.querySelectorAll('.skill-progress');
const animateSkills = () => {
    skillBars.forEach(bar => {
        const percentage = bar.getAttribute('data-progress');
        bar.style.width = percentage + '%';
    });
};

// Testimonials Slider
class TestimonialsSlider {
    constructor() {
        this.slider = document.querySelector('.testimonials-slider');
        this.slides = document.querySelectorAll('.testimonial-slide');
        this.currentSlide = 0;
        this.autoSlideInterval = 5000;
        this.init();
    }

    init() {
        this.createDots();
        this.showSlide(this.currentSlide);
        this.startAutoSlide();
    }

    createDots() {
        const dotsContainer = document.createElement('div');
        dotsContainer.classList.add('slider-dots');
        
        this.slides.forEach((_, index) => {
            const dot = document.createElement('span');
            dot.classList.add('slider-dot');
            dot.addEventListener('click', () => this.showSlide(index));
            dotsContainer.appendChild(dot);
        });
        
        this.slider.appendChild(dotsContainer);
    }

    showSlide(index) {
        this.slides.forEach(slide => slide.classList.remove('active'));
        document.querySelectorAll('.slider-dot').forEach(dot => dot.classList.remove('active'));
        
        this.slides[index].classList.add('active');
        document.querySelectorAll('.slider-dot')[index].classList.add('active');
        this.currentSlide = index;
    }

    nextSlide() {
        this.currentSlide = (this.currentSlide + 1) % this.slides.length;
        this.showSlide(this.currentSlide);
    }

    startAutoSlide() {
        setInterval(() => this.nextSlide(), this.autoSlideInterval);
    }
}

// Blog Post Filter
const filterPosts = (category) => {
    const posts = document.querySelectorAll('.blog-post');
    posts.forEach(post => {
        if (category === 'all' || post.dataset.category === category) {
            post.style.display = 'block';
        } else {
            post.style.display = 'none';
        }
    });
};

// Contact Form Validation
const contactForm = document.getElementById('contact-form');
contactForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = new FormData(contactForm);
    const data = Object.fromEntries(formData);
    
    try {
        // Burada form verilerini bir API'ye gönderebilirsiniz
        console.log('Form data:', data);
        contactForm.reset();
        alert('Mesajınız başarıyla gönderildi!');
    } catch (error) {
        console.error('Form submission error:', error);
        alert('Bir hata oluştu. Lütfen tekrar deneyin.');
    }
});

// Skill Radar Chart
const createSkillRadar = () => {
    const ctx = document.getElementById('skillRadar').getContext('2d');
    
    new Chart(ctx, {
        type: 'radar',
        data: {
            labels: [
                'Test Automation',
                'Mobile Testing',
                'API Testing',
                'Performance Testing',
                'Security Testing',
                'CI/CD',
            ],
            datasets: [{
                label: 'Skill Level',
                data: [95, 90, 85, 80, 75, 85],
                backgroundColor: 'rgba(37, 99, 235, 0.2)',
                borderColor: 'rgba(37, 99, 235, 1)',
                pointBackgroundColor: 'rgba(37, 99, 235, 1)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgba(37, 99, 235, 1)'
            }]
        },
        options: {
            scales: {
                r: {
                    angleLines: {
                        color: 'rgba(0, 0, 0, 0.1)'
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.1)'
                    },
                    pointLabels: {
                        color: getComputedStyle(document.documentElement).getPropertyValue('--text-color'),
                        font: {
                            size: 12,
                            family: "'Poppins', sans-serif"
                        }
                    },
                    ticks: {
                        backdropColor: 'transparent',
                        color: getComputedStyle(document.documentElement).getPropertyValue('--text-color')
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
};

// Initialize Components
document.addEventListener('DOMContentLoaded', () => {
    // Initialize AOS
    AOS.init({
        duration: 800,
        offset: 100,
        once: true
    });

    // Initialize Testimonials Slider
    if (document.querySelector('.testimonials-slider')) {
        new TestimonialsSlider();
    }

    // Initialize Skill Radar Chart
    if (document.getElementById('skillRadar')) {
        createSkillRadar();
    }

    // Fetch GitHub Activity
    if (document.querySelector('.github-activity')) {
        fetchGitHubActivity();
    }

    // Animate Skills
    if (document.querySelector('.skills-section')) {
        animateSkills();
    }

    // Newsletter Form
    const newsletterForm = document.getElementById('newsletter-form');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('newsletter-email').value;
            
            try {
                // Burada newsletter API'sine istek atabilirsiniz
                console.log('Newsletter subscription:', email);
                newsletterForm.reset();
                alert('Newsletter aboneliğiniz başarıyla tamamlandı!');
            } catch (error) {
                console.error('Newsletter error:', error);
                alert('Bir hata oluştu. Lütfen tekrar deneyin.');
            }
        });
    }
});

// Skills Animation
const skillTags = document.querySelectorAll('.skill-tag');
const observerOptions = {
    threshold: 0.5
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('animate');
        }
    });
}, observerOptions);

skillTags.forEach(tag => {
    observer.observe(tag);
});

// Project Cards Animation
const projectCards = document.querySelectorAll('.project-card');
projectCards.forEach(card => {
    card.addEventListener('mouseenter', () => {
        card.classList.add('hover');
    });
    card.addEventListener('mouseleave', () => {
        card.classList.remove('hover');
    });
});

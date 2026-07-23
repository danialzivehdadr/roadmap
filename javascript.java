document.addEventListener("DOMContentLoaded", () => {
    // 1. Cache DOM elements for better performance and quicker access
    const redInput = document.getElementById("red");
    const greenInput = document.getElementById("green");
    const blueInput = document.getElementById("blue");
    
    const divRed = document.getElementById("divRed");
    const divGreen = document.getElementById("divGreen");
    const divBlue = document.getElementById("divBlue");
    
    const colorHeading = document.querySelector("h2");
    const ddgElements = document.querySelectorAll(".ddg");
    const rgbDisplay = document.getElementById("rgb");

    /**
     * 2. Modern and concise decimal to hexadecimal conversion
     * Replaces the manual and lengthy previous logic
     */
    const toHex = (n) => {
        const num = Math.max(0, Math.min(255, parseInt(n, 10) || 0));
        return num.toString(16).padStart(2, '0').toUpperCase();
    };

    const rgbToHex = (r, g, b) => `#${toHex(r)}${toHex(g)}${toHex(b)}`;

    /**
     * 3. Main function to update colors
     */
    function getColor() {
        const r = parseInt(redInput?.value, 10) || 0;
        const g = parseInt(greenInput?.value, 10) || 0;
        const b = parseInt(blueInput?.value, 10) || 0;

        // Update text displays using textContent (safer and faster than innerHTML)
        if (divRed) divRed.textContent = `RED = ${r}`;
        if (divGreen) divGreen.textContent = `GREEN = ${g}`;
        if (divBlue) divBlue.textContent = `BLUE = ${b}`;

        // Apply primary background color
        const hexColor = rgbToHex(r, g, b);
        document.body.style.backgroundColor = hexColor;
        if (colorHeading) colorHeading.textContent = hexColor;
        if (rgbDisplay) rgbDisplay.textContent = hexColor;

        // Apply complementary/inverted colors to .ddg elements
        ddgElements.forEach((el, index) => {
            let compHex;
            // Preserving your original logic for inverting specific channels per element
            if (index === 0) compHex = rgbToHex(255 - r, g, b);
            else if (index === 1) compHex = rgbToHex(r, 255 - g, b);
            else compHex = rgbToHex(r, g, 255 - b);

            el.style.backgroundColor = compHex;
            el.style.color = "#ffffff";
        });
    }

    /**
     * 4. Display the selected color (Logical fallback included)
     */
    function getYourColor() {
        const currentColor = colorHeading?.textContent || rgbDisplay?.textContent || "Unknown";
        // Note: For a better User Experience (UX) in the future, consider replacing 'alert' with a Toast Notification.
        alert(`Your selected color is: ${currentColor}`);
    }

    /**
     * 5. Prompt user for a value and update the corresponding input
     */
    function promptForColor(colorName, targetInput) {
        if (!targetInput) return;
        
        const currentValue = targetInput.value;
        const promptColor = prompt(`How much ${colorName} do you need for your color?\nInsert a number from 0 to 255:`, currentValue);
        
        if (promptColor !== null) {
            const val = parseInt(promptColor, 10);
            if (!isNaN(val) && val >= 0 && val <= 255) {
                targetInput.value = val;
                getColor(); // Refresh the UI with the new value
            } else {
                alert("Please enter a valid number between 0 and 255.");
            }
        }
    }

    // 6. Attach event listeners with existence checks (prevents null reference errors)
    if (redInput) redInput.addEventListener("input", getColor);
    if (greenInput) greenInput.addEventListener("input", getColor);
    if (blueInput) blueInput.addEventListener("input", getColor);

    // Initial call to set colors on page load
    getColor();

    // 7. Expose functions to the global window object for inline HTML onclick attributes
    window.getYourColor = getYourColor;
    window.promptForColor = promptForColor;
});

document.addEventListener("DOMContentLoaded", () => {
    // It is generally best practice to avoid alerts on page load unless strictly necessary.
    // alert("Thank you for upvoting this code."); 

    // Cache DOM elements for better performance
    const redInput = document.getElementById("red");
    const greenInput = document.getElementById("green");
    const blueInput = document.getElementById("blue");
    
    const divRed = document.getElementById("divRed");
    const divGreen = document.getElementById("divGreen");
    const divBlue = document.getElementById("divBlue");
    
    const colorHeading = document.getElementsByTagName("h2")[0];
    const ddgElements = document.getElementsByClassName("ddg");

    /**
     * Converts RGB values to a Hexadecimal string.
     */
    function rgbToHex(r, g, b) {
        return toHex(r) + toHex(g) + toHex(b);
    }

    /**
     * Helper function to convert a single decimal number to a 2-digit Hex string.
     */
    function toHex(n) {
        n = parseInt(n, 10);
        if (isNaN(n)) return "00";
        n = Math.max(0, Math.min(n, 255));
        const hexChars = "0123456789ABCDEF";
        return hexChars.charAt((n - n % 16) / 16) + hexChars.charAt(n % 16);
    }

    /**
     * Main function to calculate and apply colors to the DOM.
     */
    function getColor() {
        const x = parseInt(redInput.value, 10) || 0;
        const y = parseInt(greenInput.value, 10) || 0;
        const z = parseInt(blueInput.value, 10) || 0;

        // Update text displays
        divRed.innerHTML = `RED = ${x}`;
        divGreen.innerHTML = `GREEN = ${y}`;
        divBlue.innerHTML = `BLUE = ${z}`;

        // Apply primary background color
        const hexColor = `#${rgbToHex(x, y, z)}`;
        document.body.style.backgroundColor = hexColor;
        if (colorHeading) colorHeading.innerHTML = hexColor;

        // Calculate and apply complementary/inverted colors
        const compRed = `#${rgbToHex(255 - x, y, z)}`;
        const compGreen = `#${rgbToHex(x, 255 - y, z)}`;
        const compBlue = `#${rgbToHex(x, y, 255 - z)}`;

        if (ddgElements.length >= 3) {
            ddgElements[0].style.backgroundColor = compRed;
            ddgElements[0].style.color = "#ffffff";
            
            ddgElements[1].style.backgroundColor = compGreen;
            ddgElements[1].style.color = "#ffffff";
            
            ddgElements[2].style.backgroundColor = compBlue;
            ddgElements[2].style.color = "#ffffff";
        }
    }

    /**
     * Displays the current selected color in an alert.
     */
    function getYourColor() {
        const rgbElement = document.getElementById("rgb");
        if (rgbElement && rgbElement.childNodes.length > 0) {
            const yourColor = rgbElement.childNodes[0].innerHTML;
            alert(`Your color is ${yourColor}`);
        } else {
            // Fallback if the specific 'rgb' element is missing
            const currentColor = colorHeading ? colorHeading.innerHTML : "Unknown";
            alert(`Your color is ${currentColor}`);
        }
    }

    /**
     * Replaces the non-functional 'input' function.
     * Prompts the user for a value and updates the corresponding slider/input.
     */
    function promptForColor(colorName, targetInput) {
        const currentValue = targetInput.value;
        const promptColor = prompt(`How much ${colorName} do you need for your color?\nInsert a number from 0 to 255`, currentValue);
        
        if (promptColor !== null) {
            let val = parseInt(promptColor, 10);
            if (!isNaN(val) && val >= 0 && val <= 255) {
                targetInput.value = val;
                getColor(); // Refresh the UI with the new value
            } else {
                alert("Please enter a valid number between 0 and 255.");
            }
        }
    }

    // Attach event listeners for real-time updates
    redInput.addEventListener("input", getColor);
    greenInput.addEventListener("input", getColor);
    blueInput.addEventListener("input", getColor);

    // Initial call to set colors on page load
    getColor();

    // Expose functions to the global window object if they are called via inline HTML onclick attributes
    window.getYourColor = getYourColor;
    window.promptForColor = promptForColor;
});

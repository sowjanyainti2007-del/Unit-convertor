import streamlit as st

# Set page layout and professional tab icon
st.set_page_config(
    page_title="Oasis Infobyte - Premium Unit Converter", 
    layout="centered",
    page_icon="🔄"
)

# --- Custom App Styling (CSS Injection) ---
st.markdown("""
    <style>
    /* Main application background color */
    .stApp {
        background-color: #f4f6f9;
    }
    
    /* App container card mimicking a modern mobile/web panel */
    .main-card {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 16px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        border: 1px solid #e1e4e8;
        margin-bottom: 20px;
    }
    
    /* Main title styling */
    .app-title {
        color: #1e293b;
        font-family: 'Inter', sans-serif;
        font-weight: 800;
        font-size: 32px;
        margin-bottom: 4px;
        display: flex;
        align-items: center;
        gap: 10px;
    }
    
    /* Subtitle styling */
    .app-subtitle {
        color: #64748b;
        font-size: 15px;
        margin-bottom: 24px;
    }

    /* Section Header Labels */
    .field-label {
        color: #334155;
        font-weight: 600;
        font-size: 14px;
        margin-bottom: 6px;
        display: flex;
        align-items: center;
        gap: 6px;
    }
    </style>
""", unsafe_allow_html=True)

# --- App Header Section ---
st.markdown('<div class="app-title">🔄 CoreConvert Pro</div>', unsafe_allow_html=True)
st.markdown('<div class="app-subtitle">Oasis Infobyte SIP Track Task — Advanced Unit Converter Panel</div>', unsafe_allow_html=True)

# Start wrapping our main features in a beautiful UI container card
st.markdown('<div class="main-card">', unsafe_allow_html=True)

# 1. Category Selector with Metric Icon
st.markdown('<div class="field-label">📁 Choose Measurement Category</div>', unsafe_allow_html=True)
category = st.selectbox(
    "Select Category:", 
    ["Length 📏", "Weight ⚖️", "Temperature 🌡️"], 
    label_visibility="collapsed"
)

# Normalize the string category for backend logic execution
clean_category = category.split()[0]

# Define units depending on user choice
if clean_category == "Length":
    units = ["Centimeters (cm)", "Meters (m)", "Kilometers (km)"]
elif clean_category == "Weight":
    units = ["Grams (g)", "Kilograms (kg)", "Pounds (lbs)"]
else:
    units = ["Celsius (°C)", "Fahrenheit (°F)"]

st.markdown("<br>", unsafe_allow_html=True)

# 2. Source and Target Unit Dropdowns (Side-by-Side configuration)
col1, col2 = st.columns(2)
with col1:
    st.markdown('<div class="field-label">🛫 From Unit</div>', unsafe_allow_html=True)
    from_unit = st.selectbox("From Unit:", units, key="from_unit", label_visibility="collapsed")
with col2:
    st.markdown('<div class="field-label">🛬 To Unit</div>', unsafe_allow_html=True)
    to_unit = st.selectbox("To Unit:", units, key="to_unit", label_visibility="collapsed")

st.markdown("<br>", unsafe_allow_html=True)

# 3. Input Value Field 
st.markdown('<div class="field-label">✍️ Enter Value to Convert</div>', unsafe_allow_html=True)
input_value_str = st.text_input("Enter Value:", placeholder="e.g., 100", label_visibility="collapsed")

st.markdown("<br>", unsafe_allow_html=True)

# 4. Convert Button Layout & Operational Processing Core
if st.button("⚡ Execute Conversion", type="primary", use_container_width=True):
    # Validation Checklist Checks
    if not input_value_str.strip():
        st.error("🚨 **Input Validation Error:** Please type a numeric value into the field before proceeding.")
    else:
        try:
            value = float(input_value_str)
            result = 0.0
            
            # --- Technical Mathematical Calculations ---
            if clean_category == "Length":
                if "cm" in from_unit: meters = value / 100.0
                elif "km" in from_unit: meters = value * 1000.0
                else: meters = value
                
                if "cm" in to_unit: result = meters * 100.0
                elif "km" in to_unit: result = meters / 1000.0
                else: result = meters

            elif clean_category == "Weight":
                if "kg" in from_unit: grams = value * 1000.0
                elif "lbs" in from_unit: grams = value * 453.592
                else: grams = value
                
                if "kg" in to_unit: result = grams / 1000.0
                elif "lbs" in to_unit: result = grams / 453.592
                else: grams = value

            elif clean_category == "Temperature":
                if from_unit == to_unit:
                    result = value
                elif "Celsius" in from_unit:
                    result = (value * 9/5) + 32
                else:
                    result = (value - 32) * 5/9

            # 5. Clean, Professional Output Panel
            st.markdown("---")
            st.success(f"### 🎉 Result:  \n**{value} {from_unit.split()[-1]}** is equivalent to **{result:.4f} {to_unit.split()[-1]}**")
            
        except ValueError:
            st.error("🚨 **Input Validation Error:** The value entered contains non-numeric formats. Please try again with regular numbers.")

st.markdown('</div>', unsafe_allow_html=True)

const useCity = (createChannel) =>{
    const cityList = [
        { value: "Bangalore - Koramangala", display: "Bangalore - Koramangala" },
        { value: "Gurgaon", display: "Gurgaon" },
        { value: "Pune", display: "Pune" },
        { value: "Chennai", display: "Chennai" },
        { value: "Hyderabad", display: "Hyderabad" },
        { value: "Coimbatore", display: "Coimbatore" },
        { value: "Mumbai", display: "Mumbai" },
        { value: "Bangalore - Mahadevapura", display: "Bangalore - Mahadevapura" },
      ];
    createChannel.city = cityList[0].value;
    return {
        cities: cityList,
    }
}

export default useCity;
const useCountry = (createChannel) =>{
    const countryList = [{ value: "India", display: "India" }];
    createChannel.country = countryList[0].value;
    return {
        countries: countryList,
    }
}

export default useCountry;
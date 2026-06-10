import { useState } from "react";
import type { PasswordRequest } from "../services/api";

interface props {
    onGenerate: (req: PasswordRequest) => void
    loading: boolean;
}

export function PasswordForm({ onGenerate, loading }: props) {
    const [length, setLength] = useState(16);
    const [uppercase, uppercase] = useState(true);
    const [lowercase, lowercase] = useState(true);
    const [numbers, numbers] = useState(true);
    const [symbols, symbols] = useState(true);
    const [excludeAmbiguous, serExcludeAbiguous] = useState(true);
    const [quantity, setQuantity] = useState(1);

    const noneSelected = !uppercase && !lowercase && !numbers && !symbols;

    function handleSubmit() {
        if(noneSelected) return;
        onGenerate({ length, uppercase, lowercase, numbers, symbols, excludeAmbiguous, quantity });
    }

    